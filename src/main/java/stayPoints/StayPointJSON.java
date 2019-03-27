package main.java.stayPoints;

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
public class StayPointJSON {

    public static String getStayPointsJSON(ArrayList<ArrayList<Double>> stayPointGeoList){
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
            "type": "MultiPoint",
            "coordinates": [ [102.0, 0.0], [103.0, 1.0] ..
         */
        Map multiPointObj = new LinkedHashMap();
        multiPointObj.put("type","MultiPoint");
        multiPointObj.put("coordinates", stayPointGeoList);

        featureObj.put("geometry",multiPointObj);

        geoJSON.add(featureObj);

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);

        return geoJSONtoString;
    }

    public static ArrayList<ArrayList<Double>> getStayPoints (String user, String startDate, String endDate, long Tmin,long Tmax,double Dmax){

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
        List<Gps> gpsList = gpsDAO.getGPSLocation(user, startTimestamp, endTimestamp);

        List<StayPoint> stayPointList = StayPointUtil.getStayPointsFromUser(gpsList, Tmin, Tmax, Dmax);

        ArrayList<ArrayList<Double>> stayPointGeoList = new ArrayList<>();

        for(StayPoint stayPoint: stayPointList){
            ArrayList<Double> coordinates = new ArrayList<>();
            coordinates.add(stayPoint.getLatitude());
            coordinates.add(stayPoint.getLongtitude());

            stayPointGeoList.add(coordinates);
        }

        return stayPointGeoList;
    }
}
