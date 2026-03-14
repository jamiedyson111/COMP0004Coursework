package uk.ac.ucl.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

// Converts a DataFrame into a JSON file using Jackson.
public class JSONWriter {
    // Builds a list of key-value maps then writes them as a JSON array to output.json.
    public JSONWriter(DataFrame dataFrame) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HashMap<String,String>> jsonData = new ArrayList<>();
        String[] columnNames = dataFrame.getColumnNames();
        for(int i=0;i<dataFrame.getRowsCount();i++){
            HashMap<String, String> patient = new HashMap<>();
            for(int j=0;j<columnNames.length;j++){
                patient.put(columnNames[j], dataFrame.getValue(columnNames[j], i));
            }
            jsonData.add(patient);
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"),jsonData);
    }
}
