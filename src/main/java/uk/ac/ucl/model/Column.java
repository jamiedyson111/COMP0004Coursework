package uk.ac.ucl.model;

import java.util.ArrayList;

// Represents a single column in the DataFrame, storing its name and all row values.
public class Column {
    private ArrayList<String> rows = new ArrayList<>();
    private String name;
    
    public Column(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return rows.size();
    }

    public String getRowValue(int row){
        return rows.get(row);
    }

    public void setRowValue(int row, String value){
        rows.set(row, value);
    }

    public void addRowValue(String value){
        rows.add(value);
    }

    public void removeRowValue(int row){
        rows.remove(row);
    }
}