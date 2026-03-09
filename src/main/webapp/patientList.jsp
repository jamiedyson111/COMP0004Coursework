<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>

  <%
    String[] columnNames = (String[]) request.getAttribute("columnNames");
    ArrayList<ArrayList<String>> data = (ArrayList<ArrayList<String>>) request.getAttribute("patientData");
  %>
  <table>
    <tr>
      <% if (columnNames != null) { for (String col : columnNames) { %>
        <th><%= col %></th>
      <% } } %>
      <th></th>
    </tr>
    <% if (data != null && data.size() > 0) {
         for (int i = 0; i < data.size(); i++) { %>
      <tr>
        
        <% for (int j = 0; j < data.get(i).size(); j++) { %>
          <td><a href="/patient?id=<%= data.get(i).get(0) %>"><%= data.get(i).get(j) %></a>
        <% } %>
          <td>
            <form action="/delete" method="post">
              <input type="hidden" name="patientId" value="<%= data.get(i).get(0) %>">
              <button type="submit">Delete</button>
            </form>
          </td>
      </tr>
    <% } } %>
  </table>

  <%
  Integer oldestAge = (Integer) request.getAttribute("oldestPerson");
  Integer youngestAge = (Integer) request.getAttribute("youngestPerson");
  Integer averageAge = (Integer) request.getAttribute("averageAge");
  Integer alive = (Integer) request.getAttribute("alive");
  Integer dead = (Integer) request.getAttribute("dead");
  Integer male = (Integer) request.getAttribute("male");
  Integer female = (Integer) request.getAttribute("female");
  HashMap<String, Integer> ethnicities = (HashMap<String, Integer>) request.getAttribute("ethnicityBreakdown");
%>

  <ul>
  <h2> Facts and Statistics </h2>
    <li>Age of Oldest Patient: <%= oldestAge %></li>
    <li>Age of Youngest Patient: <%= youngestAge %></li>
    <li>Average Age: <%= averageAge %></li>
    <li>Number of alive patients: <%= alive %></li>
    <li>Number of dead patients: <%= dead %></li>
    <li>Number of male patients: <%= male %></li>
    <li>Number of female patients: <%= female %></li>
    <h3>Ethnicity Demographics</h3>
    <% if (ethnicities != null) { for(HashMap.Entry<String, Integer> entry : ethnicities.entrySet()) { %>
    <li><%= entry.getKey() %>: <%= entry.getValue() %></li>
    <% } } %>
  </ul>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
