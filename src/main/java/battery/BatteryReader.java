package main.java.battery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sofia on 8/5/15.
 */
public class BatteryReader {
    private String path;
    private static final String DELIMITER = "\t";
    //attributes
    private static final int ID = 0;
    private static final int USER = 1;
    private static final int LEVEL = 2;
    private static final int PLUGGED = 3;
    private static final int TIMESTAMP = 6;         //file contains temperature and voltage that we don't need

    public BatteryReader(String path) {
        this.path = path;
    }

    public void readBattery() throws IOException {
        BufferedReader fileReader = null;

        //Create the file reader
        try {
            fileReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Read the CSV file header to skip it
        try {
            fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";

        while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
            String[] tokens = line.split(DELIMITER);

            if (tokens.length > 0) {

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(tokens[TIMESTAMP]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Timestamp timestamp = new Timestamp(date.getTime());

                BatteryDAO batteryDAO = new BatteryDAO();
                batteryDAO.insertBattery(Integer.parseInt(tokens[ID]), tokens[USER], Integer.parseInt(tokens[LEVEL]), Integer.parseInt(tokens[PLUGGED]), timestamp);

                //add users to list for dropdown menu
//                if (!MainClass.users.contains(tokens[USER]))
//                    MainClass.users.add(tokens[USER]);
            }
        }
    }
}
