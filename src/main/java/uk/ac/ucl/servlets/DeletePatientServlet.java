package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/delete")
public class DeletePatientServlet extends HttpServlet{
  // GET is not a valid way to delete; redirect to the list instead.
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    response.sendRedirect("/patientList");
  }

  // Deletion uses POST to prevent accidental deletion.
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("patientId");
        Model model = ModelFactory.getModel();
        model.deletePatient(id);
        model.saveToCSV("data/output.csv");
        response.sendRedirect("/patientList");
    }
}