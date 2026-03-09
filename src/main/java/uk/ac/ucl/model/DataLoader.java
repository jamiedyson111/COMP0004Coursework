package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataLoader {
    public DataFrame loadCSV(String filename) {
        DataFrame data = new DataFrame();
        boolean firstRow = true;

        try (Reader reader = new FileReader(filename); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for(CSVRecord record : csvParser){
                for(int i=0;i<record.size();i++){
                    if(firstRow){
                        data.addColumn(record.get(i));
                    } else {
                        String[] columnNames = data.getColumnNames();
                        data.addValue(columnNames[i], record.get(i));
                    }
                }
                firstRow = false;
            }
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}