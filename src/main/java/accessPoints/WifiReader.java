package main.java.accessPoints;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sofia on 7/28/15.
 */
public class WifiReader {
    private String path;
    private Map<String,ArrayList<AccessPointsData>> accessPoints;
    private static final String DELIMITER = "\t";
    //attributes
    private static final int ID = 0;
    private static final int USER = 1;
    private static final int SSID = 2;
    private static final int BSSID = 3;
    private static final int RSSI = 4;
    private static final int FREQUENCY = 5;
    private static final int LATITUDE = 6;
    private static final int LONGITUDE = 7;
    private static final int TIMESTAMP = 8;


    public WifiReader(String path ) {
        this.path = path;
        this.accessPoints = new HashMap();
    }

    public void readWifiFile() throws IOException {

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
        try {
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);

                if (tokens.length > 0) {

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = format.parse(tokens[TIMESTAMP]);
                    Timestamp timestamp = new Timestamp(date.getTime());

                    AccessPointsDAO accessPointsDAO = new AccessPointsDAO();
                    accessPointsDAO.insertAccessPoint(Integer.parseInt(tokens[ID]), tokens[USER],
                            tokens[SSID], tokens[BSSID], Integer.parseInt(tokens[RSSI]), Integer.parseInt(tokens[FREQUENCY]), timestamp);

                }
                //after the list is ready, calculate it
//                Map<String,Coordinates> accessPoints = AccessPointsCalculations.calculateAccessPoints();
//                for (String bssi: accessPoints.keySet()){
//                    System.out.println("---"+bssi+"---  "+accessPoints.get(bssi).getLatitude()+" "+accessPoints.get(bssi).getLongitude());
//
//                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public Map<String, ArrayList<AccessPointsData>> readAccessPointsCoordinates(){
        Map<String, ArrayList<AccessPointsData>> accessPoints = new LinkedHashMap<>();
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
        try {
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);

                if (tokens.length > 0) {

                    Coordinates coordinates = new Coordinates(Double.parseDouble(tokens[LATITUDE]), Double.parseDouble(tokens[LONGITUDE]));
                    AccessPointsData accessPointData = new AccessPointsData(coordinates, Integer.parseInt(tokens[RSSI]));

                    if (accessPoints.containsKey(tokens[BSSID])) {
                        accessPoints.get(tokens[BSSID]).add(accessPointData);
                    } else {
                        ArrayList<AccessPointsData> list = new ArrayList();
                        list.add(accessPointData);
                        accessPoints.put(tokens[BSSID], list);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessPoints;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
