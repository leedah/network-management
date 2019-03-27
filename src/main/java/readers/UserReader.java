package main.java.readers;

import main.java.accessPoints.AccessPointsDAO;
import main.java.baseStation.BaseStationsDAO;
import main.java.battery.BatteryDAO;
import main.java.gps.GpsDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sofia on 7/28/15.
 */

/*
    Makes a list of all the users found in the database
 */

public class UserReader {

    public static List<String> users;

    public UserReader() {
        users = new ArrayList<>();
    }

    public static void findUsers(){
        AccessPointsDAO accessPointsDAO = new AccessPointsDAO();
        BatteryDAO batteryDAO = new BatteryDAO();
        GpsDAO gpsDAO = new GpsDAO();
        BaseStationsDAO baseStationsDAO = new BaseStationsDAO();

        addUsers(accessPointsDAO.getUsers());
        addUsers(batteryDAO.getUsers());
        addUsers(gpsDAO.getUsers());
        addUsers(baseStationsDAO.getUsers());

        //http://stackoverflow.com/questions/13973503/sorting-strings-that-contains-number-in-java
        Collections.sort(users, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

        int extractInt(String s) {
            String num = s.replaceAll("\\D", "");
            // return 0 if no digits found
            return num.isEmpty() ? 0 : Integer.parseInt(num);
        }
        });

    }

    private static void addUsers(List<String> newUsers){
        for (String newUser : newUsers)
            if (!users.contains(newUser))
                users.add(newUser);
    }

    public static List<String> getUsers() {
        return users;
    }

    public static void setUsers(List<String> users) {
        UserReader.users = users;
    }
}

