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
import java.util.HashMap;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    Model model = ModelFactory.getModel();

    ArrayList<ArrayList<String>> patientData = model.getPatientData();
    String[] columnNames = model.getColumnNames();
    int oldestAge = model.oldestPersonAge();
    int youngestAge = model.youngestPersonAge();
    int averageAge = model.averageAge();
    int alive = model.alive();
    int dead = model.dead();
    int males = model.males();
    int females = model.females();
    int married = model.married();
    int single = model.single();
    int total = model.total();
    int unknown = total - single - married;
    HashMap<String, Integer> ethnicityBreakdown = model.ethinicityBreakdown();
    HashMap<String, Integer> ageDistribution = model.getAgeDistribution();

    request.setAttribute("patientData", patientData);
    request.setAttribute("columnNames", columnNames);
    request.setAttribute("oldestPerson", oldestAge);
    request.setAttribute("youngestPerson", youngestAge);
    request.setAttribute("averageAge", averageAge);
    request.setAttribute("alive", alive);
    request.setAttribute("dead", dead);
    request.setAttribute("male", males);
    request.setAttribute("female", females);
    request.setAttribute("ethnicityBreakdown", ethnicityBreakdown);
    request.setAttribute("columnNames", model.getColumnNames());
    request.setAttribute("ageData", ageDistribution);
    request.setAttribute("married", married);
    request.setAttribute("single", single);
    request.setAttribute("unknown", unknown);
    request.setAttribute("total", total);

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/statistics.jsp");
    dispatch.forward(request, response);
  }
}
