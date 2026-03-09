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

@WebServlet("/patient")
public class ViewPatientServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    try {
      // 1. Get the singleton instance of the Model.
      // The Model handles the actual data processing and data retrieval.
      Model model = ModelFactory.getModel();

      // 2. Retrieve the list of patient names from the model.
      ArrayList<ArrayList<String>> patientData = model.getPatientData();
      String[] columnNames = model.getColumnNames();
      String patientId = request.getParameter("id");
      
      // 3. Add the data to the request object.
      request.setAttribute("patientData", patientData);
      request.setAttribute("columnNames", columnNames);
      request.setAttribute("patientId", patientId);

      // 4. Invoke the JSP for display.
      // RequestDispatcher.forward() is used to send the request/response objects to another resource (JSP).
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patient.jsp");
      dispatch.forward(request, response);
    } catch (Exception e) {
      // 5. Exception Handling.
      // If there is an issue loading the model or data, log the error and forward to a dedicated error page.
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
