package main.java.accessPoints;

import main.java.gps.Gps;
import main.java.gps.GpsDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sofia on 10/2/15.
 */
public class AccessPointsJSON {

    public static String getAccessPointsJSON(List<AccessPoints> accessPointsList){

        //for calculating meanRSSI - <[latitude, longtitude, midRssi] , frequency >
        Map<AccessPointsData, Integer> accessPoints = AccessPointsCalculations.getAccessPointsDataFromUser(accessPointsList);


        /*"type": "FeatureCollection",
                "features": [
        */
        Map featureCollectionObj = new LinkedHashMap();
        featureCollectionObj.put("type", "FeatureCollection");

        /*"features": [
        {   type": "Feature",
            "geometry": {
        */
        JSONArray geoJSON = new JSONArray();


        /*  for each AccessPoint, show:
                ssid, meanRSSI, frequency
         */
        for(AccessPointsData accessPointsData : accessPoints.keySet()) {


        /*
            "type": "Feature",
            "properties" : {..}
            "geometry": { ..}
         */
            Map featureObj = new LinkedHashMap();
            featureObj.put("type", "Feature");

        /*
            "properties" : {"ssid": , "meanRSSI": , "frequency": }
         */
            Map properties = new LinkedHashMap();
//            properties.put("ssid", accessPoint.getSsid());

            properties.put("meanRSSI", accessPointsData.getRssi());
            properties.put("frequency", accessPoints.get(accessPointsData));

            featureObj.put("properties", properties);

        /*
            "type": "Point",
            "coordinates": [ [102.0, 0.0] ]
         */
            Map pointObj = new LinkedHashMap();
            pointObj.put("type", "Point");

            Coordinates midCoordinates = accessPointsData.getCoordinates();

            ArrayList<Double> coordinates = new ArrayList<>();
            coordinates.add(midCoordinates.getLatitude());
            coordinates.add(midCoordinates.getLongitude());
            pointObj.put("coordinates", coordinates);

            featureObj.put("geometry", pointObj);

            geoJSON.add(featureObj);
        }

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);
        System.out.print(geoJSONtoString);
        return geoJSONtoString;
    }

    public static List<AccessPoints> getAccessPoints(String user, String startDate, String endDate){

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

        /* get list of access points of user*/
        AccessPointsDAO accessPointsDAO = new AccessPointsDAO();
        return accessPointsDAO.getAccessPoints(user,startTimestamp,endTimestamp);
    }

    public static List<AccessPoints> getOptimalAccessPoints(String user, String startDate, String endDate){

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

        /* get list of access points of user*/
        AccessPointsDAO accessPointsDAO = new AccessPointsDAO();

        List<AccessPoints> accessPointsList = new ArrayList<>();

        /* get list of gps route of user*/
        GpsDAO gpsDAO = new GpsDAO();
        List<Gps> route = gpsDAO.getGPSLocation(user, startTimestamp, endTimestamp);

        Timestamp startTime;
        Timestamp endTime;
        Long duration = Long.valueOf((1 * 60) * 1000); //for 3 minutes

        for (Gps gps : route){
            startTime = gps.getTimestamp();
            endTime = new Timestamp(startTime.getTime());
            endTime.setTime(startTime.getTime() + duration);

//            System.out.println(startTime.toString().split("\\.")[0]);
//            System.out.println(endTime.toString().split("\\.")[0]);

            List<AccessPoints> accessPointsGps = accessPointsDAO.getAccessPoints(user,startTime,endTime);

            int sec = 1;
            while (accessPointsGps.size() == 0){
                sec++;
                endTime.setTime(endTime.getTime() + Long.valueOf((sec * 60) * 1000));
                accessPointsGps = accessPointsDAO.getAccessPoints(user,startTime,endTime);
            }

            /* find maxRSSI*/
            if (accessPointsGps.size() > 1){
                int rssi = -2000;
                int ap = 0;

                for(int i=0; i< accessPointsGps.size(); i++){
                    if (accessPointsGps.get(i).getRssi() > rssi) {
                        ap = i;
                        rssi = accessPointsGps.get(i).getRssi();
                    }
                }
                accessPointsList.add(accessPointsGps.get(ap));
            }
            else {
                accessPointsList.add(accessPointsGps.get(0));
//                System.out.println("rssi: "+accessPointsGps.get(0).getRssi());
            }

        }

//        for (AccessPoints accessPoints: accessPointsList)
//            System.out.println(accessPoints.getTimestamp()+" "+accessPoints.getRssi());


        return accessPointsList;

    }
}
