package main.java.battery;

import org.json.simple.JSONValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofia on 10/2/15.
 */
public class BatteryJSON {


    public static String getBatteryJSON(LinkedHashMap<String, Integer> batteryMap){

//        for (String timestamp : batteryMap.keySet())
//            System.out.println(timestamp);

        Map batteryObj = new LinkedHashMap(); //to keep order
        String batteryJsonToString;

        batteryObj.putAll(batteryMap);

        batteryJsonToString = JSONValue.toJSONString(batteryObj);
        return batteryJsonToString;

    }

    public static LinkedHashMap<String, Integer> getBatteryInfo(String user, String startDate, String endDate) {

        /* convert String with time to timestamp*/
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = format.parse(startDate+" 00:00:00");
            dateEnd = format.parse(endDate+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp startTimestamp = new Timestamp(dateStart.getTime());
        Timestamp endTimestamp = new Timestamp(dateEnd.getTime());

        BatteryDAO batteryDAO = new BatteryDAO();
        List<Battery> batteryList = batteryDAO.getBattery(user, startTimestamp, endTimestamp);

        LinkedHashMap<String, Integer> batteryMap = new LinkedHashMap<>();

        for (Battery battery: batteryList) {
            String euDate = convertDate(battery.getTimestamp());
            batteryMap.put(euDate, battery.getLevel());
        }

        return batteryMap;
    }

    public static LinkedHashMap<String, Integer> getLowBatteryHourInfo(){

        LinkedHashMap<String, Integer> lowBatteryMap = new LinkedHashMap<>();

        BatteryDAO batteryDAO = new BatteryDAO();

        for (int i=0; i<24; i++){
            String hour;

            if (i < 10)
                hour = "0" + String.valueOf(i);
            else
                hour = String.valueOf(i);


            int lowBatteryUsers = batteryDAO.getLowBatteryHour(hour);

            lowBatteryMap.put(hour, lowBatteryUsers);
        }

        return lowBatteryMap;
    }

    private static String convertDate(Timestamp timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String string  = dateFormat.format(timestamp);
//        System.out.println(string);

        return string;
    }

    private static String convertDate(String date){
        String[] hour = date.split(" ");
        String[] arr = hour[0].split("-");
        String eudate = arr[2] + "/" + arr[1] + "/" + arr[0]+" "+hour[1];

//        System.out.println(eudate);
        return eudate;
    }

    public static LinkedHashMap<String, Integer> getLowBatteryInfo(){

        LinkedHashMap<String, Integer> lowBatteryMap = new LinkedHashMap<>();

        BatteryDAO batteryDAO = new BatteryDAO();
        List<String> timestampList = batteryDAO.getAllTimestamps();

        for (String timestamp: timestampList){

            int lowBatteryUsers = batteryDAO.getLowBatteryUsers(timestamp);

            String date = convertDate(timestamp);
            lowBatteryMap.put(date, lowBatteryUsers);
        }

        return lowBatteryMap;
    }
}
