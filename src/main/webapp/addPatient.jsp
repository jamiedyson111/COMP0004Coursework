<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Add New Patient</h2>
  <form method="POST" action="/add">
    <label>ID *</label>
    <input type="text" name="ID" placeholder="ID" required/><br/>
    <label>BIRTHDATE *</label>
    <input type="text" name="BIRTHDATE" placeholder="YYYY-MM-DD" required/><br/>
    <label>DEATHDATE</label>
    <input type="text" name="DEATHDATE" placeholder="YYYY-MM-DD"/><br/>
    <label>SSN</label>
    <input type="text" name="SSN" placeholder="SSN"/><br/>
    <label>DRIVERS</label>
    <input type="text" name="DRIVERS" placeholder="DRIVERS"/><br/>
    <label>PASSPORT</label>
    <input type="text" name="PASSPORT" placeholder="PASSPORT"/><br/>
    <label>PREFIX</label>
    <input type="text" name="PREFIX" placeholder="PREFIX"/><br/>
    <label>FIRST *</label>
    <input type="text" name="FIRST" placeholder="FIRST" required/><br/>
    <label>LAST *</label>
    <input type="text" name="LAST" placeholder="LAST" required/><br/>
    <label>SUFFIX</label>
    <input type="text" name="SUFFIX" placeholder="SUFFIX"/><br/>
    <label>MAIDEN</label>
    <input type="text" name="MAIDEN" placeholder="MAIDEN"/><br/>
    <label>MARITAL</label>
    <input type="text" name="MARITAL" placeholder="MARITAL"/><br/>
    <label>RACE</label>
    <input type="text" name="RACE" placeholder="RACE"/><br/>
    <label>ETHNICITY</label>
    <input type="text" name="ETHNICITY" placeholder="ETHNICITY"/><br/>
    <label>GENDER *</label>
    <input type="text" name="GENDER" placeholder="M or F" required/><br/>
    <label>BIRTHPLACE</label>
    <input type="text" name="BIRTHPLACE" placeholder="BIRTHPLACE"/><br/>
    <label>ADDRESS</label>
    <input type="text" name="ADDRESS" placeholder="ADDRESS"/><br/>
    <label>CITY</label>
    <input type="text" name="CITY" placeholder="CITY"/><br/>
    <label>STATE</label>
    <input type="text" name="STATE" placeholder="STATE"/><br/>
    <label>ZIP</label>
    <input type="text" name="ZIP" placeholder="ZIP"/><br/>
    <button type="submit">Submit</button>
  </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>