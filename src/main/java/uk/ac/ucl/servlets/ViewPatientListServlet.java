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

// Displays a paginated table of all patients.
// Pagination is handled server-side so the browser only receives a small
// slice of data at a time, even when the dataset has 100,000+ rows.
@WebServlet("/patientList")
public class ViewPatientListServlet extends HttpServlet{
  // Limits page size to avoid overwhelming the browser with a huge HTML table.
  private static final int PAGE_SIZE = 100;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    try {
      Model model = ModelFactory.getModel();

      ArrayList<ArrayList<String>> allData = model.getPatientData();
      String[] columnNames = model.getColumnNames();

      int totalPatients = allData.size();
      int totalPages = (int) Math.ceil((double) totalPatients / PAGE_SIZE);
      if (totalPages == 0) totalPages = 1;

      // Default to page 1 if the parameter is missing or invalid.
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

      // Only pass the current page's slice to the JSP to keep the response small.
      int startIndex = (page - 1) * PAGE_SIZE;
      int endIndex = Math.min(startIndex + PAGE_SIZE, totalPatients);
      ArrayList<ArrayList<String>> pageData = new ArrayList<>(allData.subList(startIndex, endIndex));

      request.setAttribute("patientData", pageData);
      request.setAttribute("columnNames", columnNames);
      request.setAttribute("currentPage", page);
      request.setAttribute("totalPages", totalPages);
      request.setAttribute("totalPatients", totalPatients);

      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
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
