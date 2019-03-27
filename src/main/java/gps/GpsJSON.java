package main.java.gps;

import main.java.accessPoints.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sofia on 9/29/15.
 */
public class GpsJSON {

    public static String getGeoJSON(ArrayList<ArrayList<Double>> geoList){

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

        /*
            "type": "Feature",
            "geometry": {
                "type": "LineString",
         */
        Map featureObj = new LinkedHashMap();
        featureObj.put("type","Feature");

        /*
            "type": "LineString",
            "coordinates": [ [102.0, 0.0], [103.0, 1.0] ..
         */
        Map lineStringObj = new LinkedHashMap();
        lineStringObj.put("type","LineString");
        lineStringObj.put("coordinates", geoList);

        featureObj.put("geometry",lineStringObj);

        geoJSON.add(featureObj);

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);
        System.out.print(geoJSONtoString);
        return geoJSONtoString;
    }

    public static ArrayList<ArrayList<Double>> gpsRoute(String user, String startDate, String endDate){

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

        /* get list of location points of user*/
        GpsDAO gpsDAO = new GpsDAO();
        List<Coordinates> gpsList = gpsDAO.getGPSroute(user, startTimestamp, endTimestamp);

        /* in order to be compatible with geoJSON object,
            we need to convert List<Coordinates> to ArrayList<ArrayList<Double>> */

        ArrayList<ArrayList<Double>> gpsGeoList = new ArrayList<>();

        for (Coordinates coordinates : gpsList){
            ArrayList<Double> geoCoordinates = new ArrayList<>();
            geoCoordinates.add(coordinates.getLatitude());
            geoCoordinates.add(coordinates.getLongitude());
            gpsGeoList.add(geoCoordinates);
        }

        return gpsGeoList;
    }




}
