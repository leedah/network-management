package main.java.stayPoints;

import main.java.accessPoints.Coordinates;
import main.java.gps.Gps;
import main.java.gps.GpsDAO;
import main.java.readers.UserReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sofia on 10/3/15.
 */
public class PointOfInterestJSON {

    public static String getPointsOfInterestJSON(ArrayList<ArrayList<Double>> geoList){
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
            "type": "Polygon",
            "coordinates": [ [ [102.0, 0.0], [103.0, 1.0] ..
         */
        Map polygonObj = new LinkedHashMap();
        polygonObj.put("type","Polygon");

        ArrayList<ArrayList<ArrayList<Double>>> geoArrayList = new ArrayList<>();
        geoArrayList.add(geoList);
        polygonObj.put("coordinates", geoArrayList);

        featureObj.put("geometry",polygonObj);

        geoJSON.add(featureObj);

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);
        System.out.print(geoJSONtoString);
        return geoJSONtoString;

    }

//    public static ArrayList<ArrayList<Double>> applyDBCAN(List<StayPoint> allStayPoints, double eps, int minPts){
//
//        DBSCAN dbscan = new DBSCAN(eps , minPts);
//
//        List<StayPoint> dbsanResult =  dbscan.dbscan(allStayPoints);
//
//        ArrayList<ArrayList<Double>> pointsOfInterestGeoList = new ArrayList<>();
//
//        for(StayPoint stayPoint: dbsanResult){
//            ArrayList<Double> coordinates = new ArrayList<>();
//            coordinates.add(stayPoint.getLatitude());
//            coordinates.add(stayPoint.getLongtitude());
//
//            pointsOfInterestGeoList.add(coordinates);
//        }
//
//        return pointsOfInterestGeoList;
//    }

    public static String getPointsOfInterestJSON(List<List<StayPoint>> clusterPoints){

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


        /*  for each cluster, find:
                centrod, min and max point
         */
        for(List<StayPoint> cluster : clusterPoints) {

        /*
            "type": "Feature",
            "geometry": { ..}
         */
            Map featureObj = new LinkedHashMap();
            featureObj.put("type", "Feature");

        /*
            "type": "Multipoint",
            "coordinates": [ [102.0, 0.0] [...] [...]]
         */
            Map multiPointObj = new LinkedHashMap();
            multiPointObj.put("type","MultiPoint");

            ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();

            /* Centroid*/
            Coordinates centroid = StayPointComputation.estimateCentroidPoint(cluster);
            ArrayList<Double> centroidCoords = new ArrayList<>();
            centroidCoords.add(centroid.getLatitude());
            centroidCoords.add(centroid.getLongitude());

            /* find max distance*/
            Coordinates maxCoord = StayPointComputation.findMaxDistance(centroid, cluster);
            ArrayList<Double> maxCoords = new ArrayList<>();
            maxCoords.add(maxCoord.getLatitude());
            maxCoords.add(maxCoord.getLongitude());

            /* find min distance*/
            Coordinates minCoord = StayPointComputation.findMinDistance(centroid, cluster);
            ArrayList<Double> minCoords = new ArrayList<>();
            minCoords.add(minCoord.getLatitude());
            minCoords.add(minCoord.getLongitude());

            coordinates.add(centroidCoords);
            coordinates.add(maxCoords);
            coordinates.add(minCoords);

            multiPointObj.put("coordinates", coordinates);

            featureObj.put("geometry",multiPointObj);


            geoJSON.add(featureObj);
        }

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);
        System.out.print(geoJSONtoString);
        return geoJSONtoString;


    }

    public static List<List<StayPoint>> getAllStayPoints (String startDate, String endDate, long Tmin,long Tmax,double Dmax, double eps, int minPts) {
        List<StayPoint> allStayPoints = new ArrayList<>();

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

        GpsDAO gpsDAO = new GpsDAO();
        for (String user: UserReader.getUsers()){
            List<Gps> gpsList = gpsDAO.getGPSLocation(user, startTimestamp, endTimestamp);
            List<StayPoint> stayPointList = StayPointUtil.getStayPointsFromUser(gpsList, Tmin, Tmax, Dmax);

            allStayPoints.addAll(stayPointList);
        }

        DBSCAN dbscan = new DBSCAN(eps , minPts);
        List<List<StayPoint>> dbsanResult =  dbscan.dbscan(allStayPoints);
        List<List<StayPoint>> result = new ArrayList<>();

        /* remove all clusters that have only one point*/
        for (List<StayPoint> stayPointArrayList: dbsanResult){
            if (stayPointArrayList.size() > 1)
                result.add(stayPointArrayList);
        }

        return result;
    }
}
