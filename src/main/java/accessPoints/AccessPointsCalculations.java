package main.java.accessPoints;

import java.util.*;

/**
 * Created by sofia on 8/3/15.
 */
public class AccessPointsCalculations {
    public static Map<String,Coordinates> accessPointsMid; //a map with <bssisd, [latitude, longtitude]>, with the exact coordinates for each accessPoint

    /*
        input: Map<String,ArrayList<AccessPointsData>> accessPointsList
            a list with <bssid, [[latitude, lontitude], rssi]>, used for calculating the geoMidCoordinates

        output: Map<String,Coordinates>
            a map with <bssisd, [latitude, longtitude]>, with the exact coordinates for each accessPoint
     */

    public static Map<String,Coordinates> calculateAccessPoints(Map<String,ArrayList<AccessPointsData>> accessPointsList){
        Map<String,Coordinates> accessPoints = new LinkedHashMap<>();


        for (String bssi: accessPointsList.keySet()){
            Coordinates midCoordinates = calculateGeoMidCoordinates(accessPointsList.get(bssi));
            accessPoints.put(bssi,midCoordinates);
        }

        return accessPoints;
    }

    private static Coordinates calculateGeoMidCoordinates(ArrayList<AccessPointsData> accessPointsDataList){

        double lat = 0;
        double lon = 0;
        Coordinates accessPointsCoordinates = new Coordinates(lat,lon);

        double totalweight = 0;

        //for every set of Access Points Coordinates, calculate its Geographic Midpoint
        for(AccessPointsData accessPointsData : accessPointsDataList)
        {
            Coordinates coordinates = accessPointsData.getCoordinates();
            //convert clat and clon from degrees to radiants
            double clat = coordinates.getLatitude() * Math.PI / 180;
            double clon = coordinates.getLongitude() * Math.PI / 180;

            //convert dBm to mW
            double cweight = Math.pow(10, accessPointsData.getRssi()/10);
            lat += clat*cweight;
            lon += clon*cweight;
            totalweight += cweight;
        }

        //calculate the weighted latitude and longitude
        lat = lat/totalweight;
        lon = lon/totalweight;

        //convert from radiants to degrees
        double deglat = lat * 180 / Math.PI;
        double deglon = lon * 180 / Math.PI;

        accessPointsCoordinates.setLatitude(deglat);
        accessPointsCoordinates.setLongitude(deglon);

        return  accessPointsCoordinates;
    }

    /*
        Map<String, Coordinates> accessPointsExactCoordinates :
            A map of <String, [latitude, longtitude]> that contains the exact location for all accessPoints
       List<AccessPoints> accessPointsList :
            A list a specific user's Access Points at a specific period

       Map<AccessPointsData, Integer> accessPointsDataMap :
            A map of <[latitude, longtitude, midRssi] , frequency > for the user
     */
    public static Map<AccessPointsData, Integer> getAccessPointsDataFromUser ( List<AccessPoints> accessPointsList){

        Map<AccessPointsData, Integer> accessPointsDataMap = new LinkedHashMap<>();

        //Map for bssid to a list of rssi
        Map<String, ArrayList<Integer>> bssid2MidRssi = new HashMap<>();
        Map<String, Integer> bssid2frequency = new HashMap<>();

        System.out.println("AccessPointData");
        //for every accessPoint in users list, find all the rssi's and save them to map
        for (AccessPoints accessPoints : accessPointsList){
            if (bssid2MidRssi.containsKey(accessPoints.getBssid())){
                bssid2MidRssi.get(accessPoints.getBssid()).add(accessPoints.getRssi());
            }
            else  {
                ArrayList<Integer> rssiList = new ArrayList<>();
                rssiList.add(accessPoints.getRssi());
                bssid2MidRssi.put(accessPoints.getBssid(), rssiList);
                bssid2frequency.put(accessPoints.getBssid(), accessPoints.getFrequency());
            }
//            System.out.println(accessPoints.getSsid()+" "+accessPoints.getBssid());
        }

        //for all the accessPoints found, calculate the mid rssi and add it to final mal
        for (String bssid : bssid2MidRssi.keySet()){
            int size = bssid2MidRssi.get(bssid).size();
            int meanRssi = 0;

            if (size > 1) {
                int sumRssi = 0;
                for (Integer rssi : bssid2MidRssi.get(bssid)){
                    sumRssi += rssi;
                }
                meanRssi = sumRssi / size;
            }
            else
                meanRssi = bssid2MidRssi.get(bssid).get(size-1);

            Coordinates coordinates =
                    new Coordinates(accessPointsMid.get(bssid).getLatitude(), accessPointsMid.get(bssid).getLongitude());
            AccessPointsData accessPointsData = new AccessPointsData(coordinates, meanRssi);

            int frequency =  bssid2frequency.get(bssid);
            accessPointsDataMap.put(accessPointsData, frequency);

            /*String format = "%-30s%s%d%d%n";
            for (AccessPoints accessPoints: accessPointsList)
                if (accessPoints.getBssid().equals(bssid))
                    System.out.printf(format, bssid,accessPoints.getSsid(), coordinates.getLatitude(), coordinates.getLongitude());
                    System.out.println(bssid+" "+accessPoints.getSsid()+" \t\t" +meanRssi+" "+frequency);*/
        }


        return accessPointsDataMap;
    }




}
