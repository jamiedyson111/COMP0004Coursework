<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.TreeMap" %>


<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/defaultHeader.jsp"/>
<div class="main">
  <h1>Statistics</h1>

 <%
  Integer oldestAge = (Integer) request.getAttribute("oldestPerson");
  Integer youngestAge = (Integer) request.getAttribute("youngestPerson");
  Integer averageAge = (Integer) request.getAttribute("averageAge");
  Integer alive = (Integer) request.getAttribute("alive");
  Integer dead = (Integer) request.getAttribute("dead");
  Integer male = (Integer) request.getAttribute("male");
  Integer female = (Integer) request.getAttribute("female");
  Integer married = (Integer) request.getAttribute("married");
  Integer single = (Integer) request.getAttribute("single");
  Integer unknown = (Integer) request.getAttribute("unknown");
  Integer total = (Integer) request.getAttribute("total");
  
  HashMap<String, Integer> ethnicities = (HashMap<String, Integer>) request.getAttribute("ethnicityBreakdown");
  
%>

<div class="stats-wrapper">
    <div class="stats-card">
        <h2>Key Facts</h2>
        <ul>
            <li>Age of Oldest Patient: <%= oldestAge %></li>
            <li>Age of Youngest Patient: <%= youngestAge %></li>
            <li>Average Age: <%= averageAge %></li>
            <li>Total Patients: <%= total %></li>
        </ul>
    </div>
</div>

<div class="charts-row">
    <div style="chart-box">
        <h3>Alive vs Dead</h3>
        <canvas id="aliveDeadChart"></canvas>
    </div>
    <div style="chart-box">
        <h3>Male vs Female</h3>
        <canvas id="maleFemaleChart"></canvas>
    </div>
    <div style="chart-box">
        <h3>Marital Status</h3>
        <canvas id="marriedSingleChart"></canvas>
    </div>
</div>


<div class="charts-row-wide">
    <div class="chart-box-wide">
        <h3>Age Ranges</h3>
        <canvas id="ageChart"></canvas>
    </div>
    <div class="chart-box-wide">
        <h3>Ethnicities</h3>
        <canvas id="ethnicityChart"></canvas>
    </div>
</div>

<%
    HashMap<String, Integer> ageData = (HashMap<String, Integer>) request.getAttribute("ageData");
    TreeMap<String, Integer> sortedAgeData = new TreeMap<>((a, b) -> {
    int numA = Integer.parseInt(a.split("-")[0]);
    int numB = Integer.parseInt(b.split("-")[0]);
    return numA - numB;
});
sortedAgeData.putAll(ageData);

    TreeMap<String, Integer> sortedEthnicityData = new TreeMap<>();
    sortedEthnicityData.putAll(ethnicities);
%>

<script>
    var labels = [];
    var values = [];
    <%
        for (String key : sortedAgeData.keySet()) {
    %>
        labels.push("<%= key %>");
        values.push(<%= sortedAgeData.get(key) %>);
    <%
        }
    %>

    new Chart(document.getElementById("ageChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Number of Patients",
                data: values,
                backgroundColor: "rgba(54, 162, 235, 0.5)",
                borderColor: "rgba(54, 162, 235, 1)",
                borderWidth: 1
            }]
        }
    });

    
    labels = [];
    values = [];

    <%
        for (String key : sortedEthnicityData.keySet()) {
    %>
        labels.push("<%= key %>");
        values.push(<%= sortedEthnicityData.get(key) %>);
    <%
        }
    %>

    new Chart(document.getElementById("ethnicityChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Number of Patients",
                data: values,
                backgroundColor: "rgba(249, 115, 22, 0.5)",
                borderColor: "rgba(249, 115, 22, 1)",
                borderWidth: 1
            }]
        }
    });

    new Chart(document.getElementById("aliveDeadChart"), {
        type: "pie",
        data: {
            labels: ["Alive", "Dead"],
            datasets: [{
                data: [<%= alive %>, <%= dead %>],
                backgroundColor: [
                     "rgba(34, 197, 94, 0.5)",
                     "rgba(239, 68, 68, 0.5)"
                ],
                borderColor: [
                    "rgba(34, 197, 94, 1)",
                    "rgba(239, 68, 68, 1)"
                ],
                borderWidth: 1
            }]
        }
    });

    new Chart(document.getElementById("maleFemaleChart"), {
        type: "pie",
        data: {
            labels: ["Male", "Female"],
            datasets: [{
                data: [<%= male %>, <%= female %>],
                backgroundColor: [
                    "rgba(75, 192, 192, 0.5)",
                    "rgba(255, 99, 132, 0.5)"
                ],
                borderColor: [
                    "rgba(75, 192, 192, 1)",
                    "rgba(255, 99, 132, 1)"
                ],
                borderWidth: 1
            }]
        }
    });

    new Chart(document.getElementById("marriedSingleChart"), {
        type: "pie",
        data: {
            labels: ["Married", "Single", "Unknown"],
            datasets: [{
                data: [<%= married %>, <%= single %>, <%= unknown%>],
                backgroundColor: [
                    "rgba(168, 85, 247, 0.5)",   
                    "rgba(234, 179, 8, 0.5)",  
                    "rgba(148, 163, 184, 0.5)"   
                ],
                borderColor: [
                    "rgba(168, 85, 247, 1)",
                    "rgba(234, 179, 8, 1)",
                    "rgba(148, 163, 184, 1)"
                ],
                borderWidth: 1
            }]
        }
    });

    
</script>
</div>
</body>
</html>