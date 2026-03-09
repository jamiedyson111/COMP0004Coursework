<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<% String searchString = (String) request.getAttribute("searchString");
%>
<div class="main">
  <h1>Search results for: "<%= searchString != null ? searchString : "" %>"</h1>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <% } else { %>
  <%
    String[] columnNames = (String[]) request.getAttribute("columnNames");
    ArrayList<ArrayList<String>> data = (ArrayList<ArrayList<String>>) request.getAttribute("patientData");
  %>
  <table>
    <tr>
      <% if (columnNames != null && data!= null && data.size()>0) { for (String col : columnNames) { %>
        <th><%= col %></th>
      <% } } %>
    </tr>
    <% if (data != null && data.size() > 0) {
         for (int i = 0; i < data.size(); i++) { %>
      <tr>
        
        <% for (int j = 0; j < data.get(i).size(); j++) { %>
          <td><a href="/patient"><%= data.get(i).get(j) %></a></td>
        <% } %>
      </tr>
    <% } } else { %>
      <h3>No search results found</h3>
    <% } %>
  </table>
  <% } %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>