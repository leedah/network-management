package main.java.baseStation;

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
public class BaseStationsReader {
    private String path;
    private static final String DELIMITER = "\t";
    //attributes
    private static final int ID = 0;
    private static final int USER = 1;
    private static final int OPERATOR = 2;
    private static final int MCC = 3;
    private static final int MNC = 4;
    private static final int CID = 5;
    private static final int LAC = 6;
    private static final int LATITUDE = 7;
    private static final int LONGITUDE = 8;
    private static final int TIMESTAMP = 9;

    public BaseStationsReader(String path) {
        this.path = path;
    }

    public void readBaseStations() throws IOException {
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

                BaseStationsDAO baseStationsDAO = new BaseStationsDAO();

                if (tokens[LATITUDE].equalsIgnoreCase("No Latitude") || tokens[LATITUDE].equalsIgnoreCase("No Latitude yet")) {
                    if (tokens[LONGITUDE].equalsIgnoreCase("No longitude")) {
                        System.out.println(Integer.parseInt(tokens[ID])+" " +tokens[USER]);
                        baseStationsDAO.insertBaseStation(Integer.parseInt(tokens[ID]), tokens[USER], tokens[OPERATOR],
                                Integer.parseInt(tokens[MCC]), Integer.parseInt(tokens[MNC]), Integer.parseInt(tokens[CID]),
                                Integer.parseInt(tokens[LAC]), null,
                                null, timestamp);
                    }
                }
                else {
                    baseStationsDAO.insertBaseStation(Integer.parseInt(tokens[ID]), tokens[USER], tokens[OPERATOR],
                            Integer.parseInt(tokens[MCC]), Integer.parseInt(tokens[MNC]), Integer.parseInt(tokens[CID]),
                            Integer.parseInt(tokens[LAC]), Double.parseDouble(tokens[LATITUDE]),
                            Double.parseDouble(tokens[LONGITUDE]), timestamp);
                }

                //add users to list for dropdown menu
//                if (!MainClass.users.contains(tokens[USER]))
//                    MainClass.users.add(tokens[USER]);
            }
        }
    }
}
