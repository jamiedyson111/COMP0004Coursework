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
            // Note where the data file is stored in the data directory,
            // and the pathname to locate it.
            // The data should be read the file once, not every time the model is accessed!
            model.loadData("data/patients100.csv");
        }
        return model;
    }
}