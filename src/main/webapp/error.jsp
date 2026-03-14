<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Error - Patient Data App</title>
</head>
<body>
<jsp:include page="/defaultHeader.jsp"/>
<div class="main">
  <h2>Error</h2>
  <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
  <p><a href="/patientList">Back to Patient List</a></p>
</div>
</body>
</html>
