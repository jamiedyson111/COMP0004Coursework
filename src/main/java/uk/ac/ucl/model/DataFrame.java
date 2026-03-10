package uk.ac.ucl.model;

import java.util.ArrayList;

public class DataFrame {
    private ArrayList<Column> columns = new ArrayList<>();

    public void addColumn(String columnName){ 
        Column column = new Column(columnName);
        columns.add(column);
    }

    public String[] getColumnNames(){
        return columns.stream().map(Column::getName).toArray(String[]::new);
    }

    public int getRowsCount(){
        if(columns.size()>0){
            return columns.get(0).getSize();
        } else{
            return 0;
        }
    }

    public String getValue(String columnName, int row){
        for(Column cols : columns){
            if(cols.getName().equals(columnName)){
                return cols.getRowValue(row);
            }
        }
        return "Unable to find value";
    }

    public void putValue(String columnName, int row, String value){
        for(Column cols :columns){
            if(cols.getName().equals(columnName) && row<cols.getSize()){ //Check to make sure the row isn't out of bounds, 
                cols.setRowValue(row, value);
            }
        }
    }

    public void addValue(String columnName, String value){
        for(Column cols :columns){
            if(cols.getName().equals(columnName)){
                cols.addRowValue(value);
            }
        }
    }

    public ArrayList<String> getRowValues(int row){
        if(row<this.getRowsCount() && row>=0){
            ArrayList<String> rowValues = new ArrayList<>();
            for(String column : this.getColumnNames()){
                rowValues.add(this.getValue(column, row));
            }
            return rowValues;
        } else {
            return null;
        }
        
    }

    public void removeRow(int row){
        for(Column col : columns){
            col.removeRowValue(row);
        }
    }

}
