package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Model {
    private DataFrame dataFrame;

    public void loadData(String filename) {
        DataLoader loader = new DataLoader();
        dataFrame = loader.loadCSV(filename);
    }

    public String[] getColumnNames() {
        return dataFrame.getColumnNames();
    }

    public int getRowCount() {
        return dataFrame.getRowsCount();
    }

    public String getValue(String columnName, int row) {
        return dataFrame.getValue(columnName, row);
    }

    public ArrayList<ArrayList<String>> getPatientData(){
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for(int j=0;j<dataFrame.getRowsCount();j++){
            rows.add(dataFrame.getRowValues(j));
        }
        return rows;
    }

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

    public HashMap<String, Integer> calculateAges(){
        HashMap<String, Integer> ages = new HashMap<>();
        for(int i=0;i<dataFrame.getRowsCount();i++){
            String birthdate = dataFrame.getValue("BIRTHDATE", i);
            if(birthdate == null || birthdate.isEmpty()) continue;
            LocalDate birthDate = LocalDate.parse(birthdate);
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            ages.put(dataFrame.getValue("ID", i), age);
        }
        return ages;
    }

    public int oldestPersonAge(){
        HashMap<String, Integer> ages = calculateAges();
        // Exclude dead patients
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

    public int averageAge(){
        HashMap<String, Integer> ages = calculateAges();
        int sum = 0;
        for (Entry<String, Integer> entry : ages.entrySet()) {
            sum = sum + entry.getValue();
        }
        return sum / ages.size(); 
    }

    public int alive(){
        int alive = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(dataFrame.getValue("DEATHDATE", i).isEmpty()){
                alive++;
            }
        }
        return alive;
    }

    public int dead(){
        int dead = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(!dataFrame.getValue("DEATHDATE", i).isEmpty()){
                dead++;
            }
        }
        return dead;
    }

    public int males(){
        int males = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(dataFrame.getValue("GENDER", i).equals("M")){
                males++;
            }
        }
        return males;

    }

    public int females(){
        int females = 0;
        for(int i=0;i<dataFrame.getRowsCount();i++){
            if(dataFrame.getValue("GENDER", i).equals("F")){
                females++;
            }
        }
        return females;
    }

    public HashMap<String, Integer> ethinicityBreakdown(){
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

    public void addPatient(ArrayList<String> values){
        String[] columnNames = dataFrame.getColumnNames();
        for(int i=0;i<columnNames.length;i++){
            dataFrame.addValue(columnNames[i], values.get(i));
        }
    }

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

    public void saveToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write column headers
            String[] columnNames = dataFrame.getColumnNames();
            writer.write(String.join(",", columnNames) + "\n");

            // Write each row
            int rowCount = dataFrame.getRowsCount();
            for (int i = 0; i < rowCount; i++) {
                ArrayList<String> row = dataFrame.getRowValues(i);
                writer.write(String.join(",", row) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}