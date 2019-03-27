package main.java.test;

import main.java.accessPoints.AccessPointsCalculations;
import main.java.accessPoints.AccessPointsData;
import main.java.accessPoints.WifiReader;
import main.java.baseStation.BaseStationsReader;
import main.java.battery.BatteryReader;
import main.java.gps.GpsReader;
import main.java.readers.PropertyReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sofia on 10/3/15.
 */
public class Initialize {

    private static final int BASESTATION_PATH = 0;
    private static final int BATTERY_PATH = 1;
    private static final int GPS_PATH = 2;
    private static final int WIFI_PATH = 3;
    private static final int SQL_IP = 4;
    private static final int SQL_PORT = 5;
    private static final int SQL_USER = 6;
    private static final int SQL_PASS = 7;
    private static final int SQL_SCRIPT = 8;


    public static void init() {


        PropertyReader properties = new PropertyReader();  //reads the paths from property file

        String paths[] = new String[9];
        try {
            System.out.println(properties.getPropertyValues().length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            paths = properties.getPropertyValues();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String i : paths)
            System.out.println(i);

        //Create Database from script
        String url = "jdbc:mysql://" + paths[SQL_IP] + ":" + paths[SQL_PORT] + "/";
        try {
            createDatabase(url,paths[SQL_USER], paths[SQL_PASS], paths[SQL_SCRIPT]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        WifiReader wifiReader = new WifiReader(paths[WIFI_PATH]);
        BatteryReader batteryReader = new BatteryReader(paths[BATTERY_PATH]);
        GpsReader gpsReader = new GpsReader(paths[GPS_PATH]);
        BaseStationsReader baseStationsReader = new BaseStationsReader(paths[BASESTATION_PATH]);


        /* create and fill database */
        long start = System.nanoTime();
        /*System.out.println("Adding Access Points to database");
        wifiReader.readWifiFile();
        System.out.println("Access points complete!");
        System.out.println("time (sec): "+(System.currentTimeMillis()-start)/1000F);
        System.out.println("time (min): "+(System.currentTimeMillis()-start)/(60*1000F));
        System.out.println();

        System.out.println("Adding Battery to database");
        batteryReader.readBattery();
        System.out.println("Battery complete!");
        System.out.println("time (sec): "+(System.currentTimeMillis()-start)/1000F);
        System.out.println("time (min): "+(System.currentTimeMillis()-start)/(60*1000F));
        System.out.println();

        System.out.println("Adding GPS to database");
        gpsReader.readGps();
        System.out.println("GPS complete!");
        System.out.println("time (sec): "+(System.currentTimeMillis()-start)/1000F);
        System.out.println("time (min): "+(System.currentTimeMillis()-start)/(60*1000F));
        System.out.println();

        System.out.println("Adding Base Stations to database");
        baseStationsReader.readBaseStations();
        System.out.println("Base Stations complete!");
        System.out.println("time (sec): "+(System.currentTimeMillis()-start)/1000F);
        System.out.println("time (min): "+(System.currentTimeMillis()-start)/(60*1000F));
        System.out.println();*/

         /* calculate estimate point for each access point*/
        Map<String, ArrayList<AccessPointsData>> accessPointsCoordinates = wifiReader.readAccessPointsCoordinates();
        AccessPointsCalculations.accessPointsMid = AccessPointsCalculations.calculateAccessPoints(accessPointsCoordinates);

    }

    public static void createDatabase(String url, String user, String password, String sqlScript) throws SQLException {

        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);

        Connection connection = null;


		 /*Create MySql Connection*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }

        connection = DriverManager.getConnection(url, properties);

       /* try {
			*//*Initialize object for ScripRunner*//*
            ScriptRunner sr = new ScriptRunner(connection, false, false);

			*//* Give the input file to Reader*//*
            Reader reader = new BufferedReader(new FileReader(sqlScript));

			*//*Execute script*//*
            sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute "+ sqlScript +" | " + e.getMessage());
        }*/

        connection.close();

    }
}

