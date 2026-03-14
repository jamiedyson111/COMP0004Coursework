package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.ArrayList;

// Handles requests for viewing a single patient's details.
@WebServlet("/patient")
public class ViewPatientServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    try {
      Model model = ModelFactory.getModel();

      ArrayList<ArrayList<String>> patientData = model.getPatientData();
      String[] columnNames = model.getColumnNames();
      String patientId = request.getParameter("id");

      request.setAttribute("patientData", patientData);
      request.setAttribute("columnNames", columnNames);
      request.setAttribute("patientId", patientId);

      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patient.jsp");
      dispatch.forward(request, response);
    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
