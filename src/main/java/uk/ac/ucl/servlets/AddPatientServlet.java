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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    Model model = ModelFactory.getModel();
    request.setAttribute("columnNames", model.getColumnNames());
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/addPatient.jsp");
    dispatch.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Model model = ModelFactory.getModel();
    ArrayList<String> values = new ArrayList<>();
    for (String col : model.getColumnNames()) {
      String val = request.getParameter(col);
      values.add(val != null ? val : "");
    }
    model.addPatient(values);
    model.saveToCSV("data/output.csv");
    response.sendRedirect("/patientList");
  }
}
