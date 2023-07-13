package src;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {
    public static BufferedReader PropertiesCall(String fileString) {
        Properties properties = new Properties();
        BufferedReader reader = null;

        try (FileInputStream fis = new FileInputStream("Time Management System/config.properties")) { properties.load(fis);
            File DATABASE = new File(properties.getProperty(fileString));

            reader = new BufferedReader(new FileReader(DATABASE));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return reader;
    }


}