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
<jsp:include page="/patientListHeader.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p class="error-message"><%= errorMessage %></p>
  <%
    }
  %>

  <%
    String[] columnNames = (String[]) request.getAttribute("columnNames");
    ArrayList<ArrayList<String>> data = (ArrayList<ArrayList<String>>) request.getAttribute("patientData");
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPages = (Integer) request.getAttribute("totalPages");
    int totalPatients = (Integer) request.getAttribute("totalPatients");
  %>

  <p>Showing page <%= currentPage %> of <%= totalPages %> (<%= totalPatients %> patients total)</p>

  <div class="pagination">
    <% if (currentPage > 1) { %>
      <a href="/patientList?page=1">First</a>
      <a href="/patientList?page=<%= currentPage - 1 %>">Previous</a>
    <% } %>

    <%
      int startPage = Math.max(1, currentPage - 2);
      int endPage = Math.min(totalPages, currentPage + 2);
      for (int p = startPage; p <= endPage; p++) {
    %>
      <% if (p == currentPage) { %>
        <strong><%= p %></strong>
      <% } else { %>
        <a href="/patientList?page=<%= p %>"><%= p %></a>
      <% } %>
    <% } %>

    <% if (currentPage < totalPages) { %>
      <a href="/patientList?page=<%= currentPage + 1 %>">Next</a>
      <a href="/patientList?page=<%= totalPages %>">Last</a>
    <% } %>
  </div>

  <table>
    <tr>
      <% if (columnNames != null) { for (String col : columnNames) { %>
        <th><%= col %></th>
      <% } } %>
      <th></th>
    </tr>
    <% if (data != null && data.size() > 0) {
         for (int i = 0; i < data.size(); i++) { %>
      <tr onclick="window.location='/patient?id=<%= data.get(i).get(0) %>'">
        <% for (int j = 0; j < data.get(i).size(); j++) { %>
          <td><a href="/patient?id=<%= data.get(i).get(0) %>"><%= data.get(i).get(j) %></a></td>
        <% } %>
          <td>
            <form action="/delete" method="post">
              <input type="hidden" name="patientId" value="<%= data.get(i).get(0) %>">
              <button type="submit" onclick="event.stopPropagation()">Delete</button>
            </form>
          </td>
      </tr>
    <% } } %>
  </table>

  <div class="pagination">
    <% if (currentPage > 1) { %>
      <a href="/patientList?page=1">First</a>
      <a href="/patientList?page=<%= currentPage - 1 %>">Previous</a>
    <% } %>

    <%
      for (int p = startPage; p <= endPage; p++) {
    %>
      <% if (p == currentPage) { %>
        <strong><%= p %></strong>
      <% } else { %>
        <a href="/patientList?page=<%= p %>"><%= p %></a>
      <% } %>
    <% } %>

    <% if (currentPage < totalPages) { %>
      <a href="/patientList?page=<%= currentPage + 1 %>">Next</a>
      <a href="/patientList?page=<%= totalPages %>">Last</a>
    <% } %>
  </div>

</div>
</body>
</html>
