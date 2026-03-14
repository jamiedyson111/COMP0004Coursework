<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Arrays" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/defaultHeader.jsp"/>
<div class="main">
  <h2>Add New Patient</h2>
  <form method="POST" action="/add">
    <%
      String[] columnNames = (String[]) request.getAttribute("columnNames");
      Set<String> requiredFields = new HashSet<>(Arrays.asList("ID", "BIRTHDATE", "FIRST", "LAST", "GENDER"));
      if (columnNames != null) {
        for (String col : columnNames) {
          boolean isRequired = requiredFields.contains(col);
    %>
      <label><%= col %><%= isRequired ? " *" : "" %></label>
      <input type="text" name="<%= col %>" placeholder="<%= col %>"
        <%= isRequired ? "required" : "" %>/><br/>
    <%
        }
      }
    %>
    <button type="submit">Submit</button>
  </form>
</div>
</body>
</html>