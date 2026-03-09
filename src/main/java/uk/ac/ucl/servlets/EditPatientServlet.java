package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/update")
public class EditPatientServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    response.sendRedirect("/patientList");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("patientId");
    Model model = ModelFactory.getModel();

    for (int i = 0; request.getParameter("columnName" + i) != null; i++) {
      String columnName = request.getParameter("columnName" + i);
      String newValue = request.getParameter("newValue" + i);
      model.editPatient(id, columnName, newValue);
    }
    model.saveToCSV("data/output.csv");
    response.sendRedirect("/patientList");

  }
}
