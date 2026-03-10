<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/defaultHeader.jsp"/>
<% String searchString = (String) request.getAttribute("searchString");
%>
<div class="main">
  <h1>Search results for: "<%= searchString != null ? searchString : "" %>"</h1>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p class="error-message"><%= errorMessage %></p>
  <% } else { %>
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
          <tr onclick="window.location='/patient?id=<%= data.get(i).get(0) %>'">
              <% for (int j = 0; j < data.get(i).size(); j++) { %>
                  <td><a href="/patient?id=<%= data.get(i).get(0) %>"><%= data.get(i).get(j) %></a></td>
              <% } %>
              <td>
                  <form action="/delete" method="post">
                      <input type="hidden" name="patientId" value="<%= data.get(i).get(0) %>">
                      <button type="submit" class="btn-danger btn-sm" onclick="event.stopPropagation()">Delete</button>
                  </form>
              </td>
          </tr>
      <% } } else { %>
          <tr><td colspan="<%= columnNames != null ? columnNames.length + 1 : 1 %>"><h3>No results found</h3></td></tr>
      <% } %>
  </table>
  <% } %>
</div>
</body>
</html>