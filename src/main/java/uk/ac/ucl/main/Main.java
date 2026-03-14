package uk.ac.ucl.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

// Starts an embedded Tomcat server so the application can run without
// needing a standalone Tomcat installation or a packaged WAR file.
public class Main
{
  private static final int DEFAULT_PORT = 8080;
  private static final String DEFAULT_WEBAPP_DIR = "src/main/webapp/";
  private static final String DEFAULT_TARGET_CLASSES = "target/classes";
  // Mapping build output here lets Tomcat find our servlets without a WAR.
  private static final String WEB_INF_CLASSES = "/WEB-INF/classes";
  private static final String LOGFILE = "logfile.txt";

  // Port and directory settings can be overridden via system properties or
  // environment variables so the same build works across different environments.
  private static int getPort() {
    String port = System.getProperty("SERVER_PORT", System.getenv("SERVER_PORT"));
    return (port != null) ? Integer.parseInt(port) : DEFAULT_PORT;
  }

  private static String getWebappDir() {
    String dir = System.getProperty("WEBAPP_DIR", System.getenv("WEBAPP_DIR"));
    return (dir != null) ? dir : DEFAULT_WEBAPP_DIR;
  }

  private static String getClassesDir() {
    String dir = System.getProperty("CLASSES_DIR", System.getenv("CLASSES_DIR"));
    return (dir != null) ? dir : DEFAULT_TARGET_CLASSES;
  }

  // Ensures Tomcat releases its port and threads on Ctrl+C or SIGTERM,
  // preventing orphaned processes.
  public static Thread addShutdown(final Tomcat tomcat, final Logger logger)
  {
    Thread shutdownHook = new Thread(() -> {
      try
      {
        if (tomcat != null)
        {
          tomcat.stop();
          tomcat.destroy();
          logger.info("Tomcat has shut down normally.");
        }
      } catch (Exception e)
      {
        logger.log(Level.SEVERE, "Error shutting down Tomcat", e);
      }
    });
    Runtime.getRuntime().addShutdownHook(shutdownHook);
    return shutdownHook;
  }

  private static Logger initialiseLogger()
  {
    Logger logger = Logger.getLogger(Main.class.getName());
    // Prevent duplicate log lines from the default parent handler.
    logger.setUseParentHandlers(false);

    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.INFO);
    logger.addHandler(consoleHandler);

    try {
      FileHandler fileHandler = new FileHandler(LOGFILE);
      fileHandler.setFormatter(new SimpleFormatter());
      fileHandler.setLevel(Level.INFO);
      logger.addHandler(fileHandler);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to create log file", e);
    }

    logger.setLevel(Level.INFO);
    return logger;
  }

  private static Context getContext(Path webappDirectory, Tomcat tomcat)
  {
    if (!Files.exists(webappDirectory) || !Files.isDirectory(webappDirectory))
    {
      throw new IllegalArgumentException("Webapp directory does not exist: " + webappDirectory);
    }
    return tomcat.addWebapp("/", webappDirectory.toAbsolutePath().toString());
  }

  // Maps the Maven build output directory into /WEB-INF/classes so Tomcat
  // can discover our servlets during development without packaging a WAR.
  private static void setResources(Context context, Path targetClassesDirectory)
  {
    WebResourceRoot resources = new StandardRoot(context);
    resources.addPreResources(new DirResourceSet(resources, WEB_INF_CLASSES,
      targetClassesDirectory.toAbsolutePath().toString(), "/"));
    context.setResources(resources);
  }

  public static void main(String[] args)
  {
    final Logger logger = initialiseLogger();
    final int port = getPort();
    final Path webappDirectory = Paths.get(getWebappDir());
    final Path targetClassesDirectory = Paths.get(getClassesDir());
    final Tomcat tomcat = new Tomcat();

    try
    {
      tomcat.setPort(port);
      // getConnector() must be called explicitly in embedded mode to
      // force creation of the default HTTP connector.
      tomcat.getConnector();
      addShutdown(tomcat, logger);

      Context context = getContext(webappDirectory, tomcat);
      setResources(context, targetClassesDirectory);

      tomcat.start();
      logger.info("Server started successfully on port " + port);
      // Block the main thread so the JVM stays alive while Tomcat
      // handles requests on its own threads.
      tomcat.getServer().await();
    }
    catch (IllegalArgumentException e)
    {
      logger.log(Level.SEVERE, "Configuration error", e);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Error occurred while starting the server", e);
    }
  }
}
