Patient Data App — README

This application is a Java web application for managing and analysing patient
records, built using Java Servlets, JSP, and an embedded Apache Tomcat server.

Features Implemented:
- Patient list view displaying all records in a sortable table
- Individual patient detail view
- Add new patient with a form (19 fields including ID, name, DOB, gender, etc.)
- Edit existing patient records
- Delete patients from the database
- Search across all fields with keyword matching
- Statistics dashboard showing:
    - Oldest, youngest, and average patient age
    - Alive vs dead breakdown (pie chart)
    - Male vs female breakdown (pie chart)
    - Marital status breakdown (pie chart)
    - Age distribution by decade (bar chart)
    - Ethnicity breakdown (bar chart)
- Export data as CSV or JSON
- Data persistence via CSV file storage

Architecture:
- MVC pattern: Model classes handle data logic, Servlets act as controllers,
  and JSP pages handle the view layer
- 9 separate servlets, one per endpoint
- Model layer with DataFrame/Column structure for tabular data management
- ModelFactory provides a singleton Model instance
- All styling in an external CSS file

To run: mvn clean compile exec:exec
Then open http://localhost:8080

Highlighted feature: The statistics page uses Chart.js to render interactive
pie and bar charts for patient demographics, giving a visual overview of the
dataset at a glance.
