package uk.ac.ucl.model;

import java.io.IOException;


public class ModelFactory
{
    private static Model model;

    public static Model getModel() throws IOException
    {
        if (model == null)
        {
            model = new Model();
            // Singleton pattern: load the CSV once and reuse the same Model instance across all servlets to avoid re-reading a large file on every request.
            model.loadData("data/patients1000.csv"); //Replace 1000 with 100, 10000 or 100000 to access different files
        }
        return model;
    }
}