package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


//Creates a functional download button for the JSON File 
@WebServlet("/downloadJSON")
public class JSONDownloadServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    Model model = ModelFactory.getModel();
    model.writeJSON();
    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "attachment; filename=patients.json");
    File file = new File("output.json");
    Files.copy(file.toPath(), response.getOutputStream());
  }
}