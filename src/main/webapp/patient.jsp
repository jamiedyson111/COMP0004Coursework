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
    String patientID = (String) request.getAttribute("patientId");
      int patientRow = -1;
      if (data != null && patientID != null) {
        for (int i = 0; i < data.size(); i++) {
          if (data.get(i).get(0).equals(patientID)) {
            patientRow = i;
            break;
          }
        }
      }
  %>

    <% if (patientRow != -1) { %>
      <form action="/update" method="post">
        <input type="hidden" name="patientId" value="<%= data.get(patientRow).get(0) %>"/>
        <% for (int j = 0; j < data.get(patientRow).size(); j++) { %>
          <input type="hidden" name="columnName<%= j %>" value="<%= columnNames[j] %>"/>
          <label><%= columnNames[j] %>:</label>
          <input type="text" name="newValue<%= j %>" value="<%= data.get(patientRow).get(j) %>"/><br/>
        <% } %>
        <button type="submit">Save</button>
      <% } %>
      </form>
          <form action="/delete" method="post">
            <input type="hidden" name="patientId" value="<%= data.get(patientRow).get(0) %>">
            <button type="submit">Delete</button>
          </form>

  

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
