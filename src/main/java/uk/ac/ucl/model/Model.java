package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

// Class that provides all data access and business logic to the servlets.
public class Model {
    private DataFrame dataFrame;

    public void loadData(String filename) throws IOException {
        DataLoader loader = new DataLoader();
        dataFrame = loader.loadCSV(filename);
    }

    // Prevent servlets from interacting directly with the dataframe
    public String[] getColumnNames() {
        return dataFrame.getColumnNames();
    }

    public int getRowCount() {
        return dataFrame.getRowsCount();
    }

    public String getValue(String columnName, int row) {
        return dataFrame.getValue(columnName, row);
    }

    // Converts the  DataFrae into a list of rows so that the patients can be displayed
    public ArrayList<ArrayList<String>> getPatientData(){
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for(int j=0;j<dataFrame.getRowsCount();j++){
            rows.add(dataFrame.getRowValues(j));
        }
        return rows;
    }

    // Search function that identifies strings regardless of the column or row they are in
    public ArrayList<ArrayList<String>> searchFor(String searchString) {
        ArrayList<ArrayList<String>> values = new ArrayList<>();
        for(int j=0;j<dataFrame.getRowsCount();j++){
            for(String name : dataFrame.getColumnNames()){
                if(dataFrame.getValue(name, j).toLowerCase().contains(searchString.toLowerCase())){
                    values.add(dataFrame.getRowValues(j));
                    break;
                }
            }
        }
        return values;
    }

    // Hashmap with patient ID and age
    public HashMap<String, Integer> calculateAges(){
        HashMap<String, Integer> ages = new HashMap<>();
        for(int i=0;i<dataFrame.getRowsCount();i++){
            String birthdate = dataFrame.getValue("BIRTHDATE", i);
            // Skip patients with no birthdate
            if(birthdate == null || birthdate.isEmpty()) continue;
            LocalDate birthDate = LocalDate.parse(birthdate);
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            ages.put(dataFrame.getValue("ID", i), age);
        }
        return ages;
    }

    // Dead patients are excluded
    public int oldestPersonAge(){
        HashMap<String, Integer> ages = calculateAges();
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(!dataFrame.getValue("DEATHDATE", i).isEmpty()){
                ages.remove(dataFrame.getValue("ID", i));
            }
        }
        return Collections.max(ages.values());
    }

    public int youngestPersonAge(){
        HashMap<String, Integer> ages = calculateAges();
        return Collections.min(ages.values());
    }

    // Uses integer division which truncates the decimal, giving a whole-number average.
    public int averageAge(){
        HashMap<String, Integer> ages = calculateAges();
        int sum = 0;
        for (Entry<String, Integer> entry : ages.entrySet()) {
            sum = sum + entry.getValue();
        }
        return sum / ages.size();
    }

    // Shared function that avoids duplicating the same loop-and-count pattern across males/females/single/married.
    private int countByColumnValue(String column, String value){
        int count = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(dataFrame.getValue(column, i).equals(value)){
                count++;
            }
        }
        return count;
    }

    // Derived from total - dead to avoid a separate loop over all rows.
    public int alive(){
        return total() - dead();
    }

    // Counts the number of dead patients, must be done separately as there is no specific value being looked for
    
    public int dead(){
        int count = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(!dataFrame.getValue("DEATHDATE", i).isEmpty()){
                count++;
            }
        }
        return count;
    }

    public int males(){
        return countByColumnValue("GENDER", "M");
    }

    public int females(){
        return countByColumnValue("GENDER", "F");
    }

    public int single(){
        return countByColumnValue("MARITAL", "S");
    }

    public int married(){
        return countByColumnValue("MARITAL", "M");
    }

    public int total(){
        return dataFrame.getRowsCount();
    }

    // Counts how many patients belong to each ethnicity, used by the statistics page to build a bar chart breakdown.
    public HashMap<String, Integer> ethnicityBreakdown(){
        HashMap<String, Integer> ethnicities = new HashMap<>();
        for(int i=0;i<dataFrame.getRowsCount();i++){
            String ethnicity = dataFrame.getValue("ETHNICITY", i);
            if(ethnicities.containsKey(ethnicity)){
                ethnicities.replace(ethnicity, 1 + ethnicities.get(ethnicity));
            } else{
                ethnicities.put(ethnicity, 1);
            }
        }
        return ethnicities;
    }

    // Values must be in the same order as getColumnNames() so each value is added to the correct column in the DataFrame.
    public void addPatient(ArrayList<String> values){
        String[] columnNames = dataFrame.getColumnNames();
        for(int i=0;i<columnNames.length;i++){
            dataFrame.addValue(columnNames[i], values.get(i));
        }
    }

    // Finds the patient by ID then updates a single column value.
    public void editPatient(String ID, String column, String newValue){
        for(int i=0;i<getRowCount();i++){
            if(dataFrame.getValue("ID", i).equals(ID)){
                dataFrame.putValue(column, i, newValue);
            }
        }
    }


    public void deletePatient(String ID){
        for(int i=0;i<getRowCount();i++){
            if(dataFrame.getValue("ID", i).equals(ID)){
                dataFrame.removeRow(i);
                break;
            }
        }
    }

    // Saves any changes back to a CSV file so that data is not lost when the server restarts.
    public void saveToCSV(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            String[] columnNames = dataFrame.getColumnNames();
            writer.write(String.join(",", columnNames) + "\n");

            int rowCount = dataFrame.getRowsCount();
            for (int i = 0; i < rowCount; i++) {
                ArrayList<String> row = dataFrame.getRowValues(i);
                writer.write(String.join(",", row) + "\n");
            }
        }
    }

    public void writeJSON() throws IOException {
        new JSONWriter(dataFrame);
    }

    // Groups ages into decade-wide ranges (0-9, 10-19, etc.) for the statistics page bar chart.
    public HashMap<String, Integer> getAgeDistribution() {
        HashMap<String, Integer> distribution = new HashMap<>();
        HashMap<String, Integer> ages = calculateAges();
        for(int age: ages.values()){
            int decade = (age/10) * 10;
            String group = decade + "-" + (decade + 9);
            distribution.put(group, distribution.getOrDefault(group, 0) + 1);
        }
        return distribution;
    }
}
