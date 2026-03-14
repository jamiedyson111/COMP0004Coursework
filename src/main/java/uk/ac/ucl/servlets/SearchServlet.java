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

// Handles search requests from the search bar in the header.
// Supports both GET and POST so that searches work whether triggered by a form submission or a direct URL with a query parameter.
@WebServlet("/runsearch")
public class SearchServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String searchString = request.getParameter("searchstring");

    try {
      Model model = ModelFactory.getModel();

      // Treat an empty search as a request to show all patients.
      if (searchString == null || searchString.trim().isEmpty()) {
        ArrayList<ArrayList<String>> allData = model.getPatientData();
        request.setAttribute("patientData", allData);
        request.setAttribute("columnNames", model.getColumnNames());
      } else {
        ArrayList<ArrayList<String>> searchResult = model.searchFor(searchString);
        request.setAttribute("patientData", searchResult);
        request.setAttribute("columnNames", model.getColumnNames());
      }
      request.setAttribute("searchString", searchString);

      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
      dispatch.forward(request, response);

    } catch (IOException e) {
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }
  }
}
