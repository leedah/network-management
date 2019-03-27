package main.java.baseStation;

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
public class BaseStationsJSON {

    public static String getBaseStationsJSON(List<BaseStations> baseStationsList){

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


        /*  for each Base Station, show:
                operator, mcc, mnc, lac, cid
         */
        for(BaseStations baseStation : baseStationsList) {


        /*
            "type": "Feature",
            "properties" : {..}
            "geometry": { ..}
         */
            Map featureObj = new LinkedHashMap();
            featureObj.put("type", "Feature");

        /*
            "properties" : {"operator": , "mcc": , "mnc": , "lac": , "cid":}
         */
            Map properties = new LinkedHashMap();
            properties.put("operator", baseStation.getOperator());
            properties.put("mcc", baseStation.getMcc());
            properties.put("mnc", baseStation.getMnc());
            properties.put("lac", baseStation.getLac());
            properties.put("cid", baseStation.getCid());

            featureObj.put("properties", properties);

        /*
            "type": "Point",
            "coordinates": [ [102.0, 0.0] ]
         */
            Map pointObj = new LinkedHashMap();
            pointObj.put("type", "Point");

            ArrayList<Double> coordinates = new ArrayList<>();
            coordinates.add(baseStation.getLatitude());
            coordinates.add(baseStation.getLongtitude());
            pointObj.put("coordinates", coordinates);

            featureObj.put("geometry", pointObj);

            geoJSON.add(featureObj);
        }

        featureCollectionObj.put("features",geoJSON);

        String geoJSONtoString = JSONValue.toJSONString(featureCollectionObj);
        System.out.print(geoJSONtoString);
        return geoJSONtoString;

    }

    public static List<BaseStations> getBaseStationList(String user, String startDate, String endDate){

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

        BaseStationsDAO baseStationsDAO = new BaseStationsDAO();
        List<BaseStations> baseStationsList = baseStationsDAO.getBaseStationsGeo(user, startTimestamp, endTimestamp);

        return baseStationsList;

    }
}
