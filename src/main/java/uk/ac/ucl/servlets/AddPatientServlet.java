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

@WebServlet("/add")
public class AddPatientServlet extends HttpServlet{
  // GET displays the empty form; column names are passed so the JSP can generate form fields without hardcoding them.
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    Model model = ModelFactory.getModel();
    request.setAttribute("columnNames", model.getColumnNames());
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/addPatient.jsp");
    dispatch.forward(request, response);
  }

  // POST collects the submitted form values and adds a new patient row.
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Model model = ModelFactory.getModel();
    ArrayList<String> values = new ArrayList<>();
    for (String col : model.getColumnNames()) {
      // If a field was left blank the form may not send a parameter at all.
      String val = request.getParameter(col);
      values.add(val != null ? val : "");
    }
    model.addPatient(values);
    model.saveToCSV("data/output.csv");
    response.sendRedirect("/patientList");
  }
}
