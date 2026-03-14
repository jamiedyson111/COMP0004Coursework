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

@WebServlet("/patientList")
public class ViewPatientListServlet extends HttpServlet{
  // Number of patients displayed per page.
  private static final int PAGE_SIZE = 100;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    try {
      // 1. Get the singleton instance of the Model.
      // The Model handles the actual data processing and data retrieval.
      Model model = ModelFactory.getModel();

      // 2. Retrieve the full list of patient data from the model.
      ArrayList<ArrayList<String>> allData = model.getPatientData();
      String[] columnNames = model.getColumnNames();

      // 3. Calculate pagination values.
      int totalPatients = allData.size();
      int totalPages = (int) Math.ceil((double) totalPatients / PAGE_SIZE);
      if (totalPages == 0) totalPages = 1;

      // 4. Parse and validate the requested page number.
      int page = 1;
      String pageParam = request.getParameter("page");
      if (pageParam != null) {
        try {
          page = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) {
          page = 1;
        }
      }
      if (page < 1) page = 1;
      if (page > totalPages) page = totalPages;

      // 5. Extract only the current page's slice of data.
      int startIndex = (page - 1) * PAGE_SIZE;
      int endIndex = Math.min(startIndex + PAGE_SIZE, totalPatients);
      ArrayList<ArrayList<String>> pageData = new ArrayList<>(allData.subList(startIndex, endIndex));

      // 6. Add the data and pagination info to the request object.
      request.setAttribute("patientData", pageData);
      request.setAttribute("columnNames", columnNames);
      request.setAttribute("currentPage", page);
      request.setAttribute("totalPages", totalPages);
      request.setAttribute("totalPatients", totalPatients);

      // 7. Invoke the JSP for display.
      // RequestDispatcher.forward() is used to send the request/response objects to another resource (JSP).
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
      dispatch.forward(request, response);
    } catch (Exception e) {
      // 8. Exception Handling.
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
