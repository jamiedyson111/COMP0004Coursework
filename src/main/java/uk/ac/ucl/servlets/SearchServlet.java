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

/**
 * The SearchServlet handles HTTP requests for performing patient searches.
 * It is mapped to the URL "/runsearch".
 *
 * This servlet demonstrates:
 * 1. Handling both GET and POST requests.
 * 2. Interacting with a Model via a Factory pattern.
 * 3. Input validation.
 * 4. Error handling and forwarding to error pages.
 * 5. Request-scoped attribute passing to JSPs for rendering results.
 */
@WebServlet("/runsearch")
public class SearchServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 1. Retrieve the search term from the request parameter.
    // The "searchstring" parameter name matches the 'name' attribute of the input field in search.html.
    String searchString = request.getParameter("searchstring");

    try {
      // 2. Get the singleton instance of the Model.
      // The Model handles the actual data processing and search logic.
      Model model = ModelFactory.getModel();

      // 3. Basic validation of search input.
      if (searchString == null || searchString.trim().isEmpty()) {
        // If the user didn't enter anything, show all patients.
        ArrayList<ArrayList<String>> allData = model.getPatientData();
        request.setAttribute("patientData", allData);
        request.setAttribute("columnNames", model.getColumnNames());
      } else {
        // 4. Perform the search and store the results in a request attribute.
        // This makes the 'result' list accessible to the JSP page.
        ArrayList<ArrayList<String>> searchResult = model.searchFor(searchString);
        request.setAttribute("patientData", searchResult);
        request.setAttribute("columnNames", model.getColumnNames());
      }
      request.setAttribute("searchString", searchString);

      // 5. Forward the request to the JSP page for display.
      // RequestDispatcher.forward() is used to send the request/response objects to another resource (JSP).
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
      dispatch.forward(request, response);

    } catch (IOException e) {
      // 6. Exception Handling.
      // If there is an issue loading the model or data, log the error and forward to a dedicated error page.
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }
  }
}
