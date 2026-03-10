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

@WebServlet("/downloadCSV")
public class CSVDownloadServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    Model model = ModelFactory.getModel();
    model.saveToCSV("patients.csv");
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; filename=patients.csv");
    File file = new File("patients.csv");
    Files.copy(file.toPath(), response.getOutputStream());
  }
}