package main.java.stayPoints;

import main.java.accessPoints.Coordinates;
import main.java.gps.Gps;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sofia on 9/24/15.
 */
public class StayPointComputation {


    public static StayPoint estimateStayPoint(List<Gps> pointsList)
    {
        Coordinates centroidCoordinates = estimateCentroid(pointsList);
        int last = pointsList.size() - 1;

        Timestamp Tstart = pointsList.get(0).getTimestamp();
        Timestamp Tend = pointsList.get(last).getTimestamp();

        StayPoint stayPoint = new StayPoint(centroidCoordinates.getLatitude(), centroidCoordinates.getLongitude(), Tstart, Tend);

//        System.out.println("Stay Point");
//        System.out.println(stayPoint.getLatitude()+" "+stayPoint.getLongtitude()+" "+stayPoint.getTstart());
        return stayPoint;
    }

    public static Coordinates estimateCentroid(List<Gps> pointsList){

        double centroidLatSum = 0;
        double centroidLonSum = 0;

        for (Gps point : pointsList){
            centroidLatSum += point.getLatitude();
            centroidLonSum += point.getLongtitude();
        }

        double centroidLat = centroidLatSum / pointsList.size();
        double centroidLon = centroidLonSum / pointsList.size();

        Coordinates centroidCoordinates = new Coordinates(centroidLat, centroidLon);

        return centroidCoordinates;
    }

    public static Coordinates estimateCentroidPoint(List<StayPoint> pointsList){

        double centroidLatSum = 0;
        double centroidLonSum = 0;

        for (StayPoint point : pointsList){
            centroidLatSum += point.getLatitude();
            centroidLonSum += point.getLongtitude();
        }

        double centroidLat = centroidLatSum / pointsList.size();
        double centroidLon = centroidLonSum / pointsList.size();

        Coordinates centroidCoordinates = new Coordinates(centroidLat, centroidLon);

        return centroidCoordinates;
    }

    public static Coordinates findMaxDistance(Coordinates stayPoint, List<StayPoint> pointList){
        double maxDistance = 0;
        double distance;
        Coordinates coordinates = new Coordinates(0, 0);

        for (StayPoint sp : pointList){
            distance = distance(sp.getLatitude(), sp.getLongtitude(), stayPoint.getLatitude(), stayPoint.getLongitude());

            if (distance > maxDistance){
                coordinates.setLatitude(sp.getLatitude());
                coordinates.setLongitude(sp.getLongtitude());

                maxDistance = distance;
            }
        }
        return coordinates;
    }

    public static Coordinates findMinDistance(Coordinates stayPoint, List<StayPoint> pointList){
        double minDistance = 0;
        double distance;
        Coordinates coordinates = new Coordinates(0, 0);

        for (StayPoint sp : pointList){
            distance = distance(sp.getLatitude(), sp.getLongtitude(), stayPoint.getLatitude(), stayPoint.getLongitude());
            if (minDistance == 0)
                minDistance = distance;

            if (distance <= minDistance){
                coordinates.setLatitude(sp.getLatitude());
                coordinates.setLongitude(sp.getLongtitude());

                minDistance = distance;
            }
        }
        return coordinates;
    }

    //http://stackoverflow.com/questions/582278/how-to-calculate-the-difference-between-two-java-java-sql-timestamps/582487#582487
    public static long timeDiff(java.util.Date t1, java.util.Date t2) {
        // Make sure the result is always > 0
        if (t1.compareTo(t2) < 0) {
            java.util.Date tmp = t1;
            t1 = t2;
            t2 = tmp;
        }

        // Timestamps mix milli and nanoseconds in the API, so we have to separate the two
        long diffSeconds = (t1.getTime() / 1000) - (t2.getTime() / 1000);
        // For normals dates, we have millisecond precision
        int nano1 = ((int) t1.getTime() % 1000) * 1000000;
        // If the parameter is a Timestamp, we have additional precision in nanoseconds
        if (t1 instanceof Timestamp)
            nano1 = ((Timestamp) t1).getNanos();
        int nano2 = ((int) t2.getTime() % 1000) * 1000000;
        if (t2 instanceof Timestamp)
            nano2 = ((Timestamp) t2).getNanos();

        int diffNanos = nano1 - nano2;
        if (diffNanos < 0) {
            // Borrow one second
            diffSeconds--;
            diffNanos += 1000000000;
        }

        // mix nanos and millis again
        Timestamp result = new Timestamp((diffSeconds * 1000) + (diffNanos / 1000000));
        // setNanos() with a value of in the millisecond range doesn't affect the value of the time field
        // while milliseconds in the time field will modify nanos! Damn, this API is a *mess*
        result.setNanos(diffNanos);
        //return result;
        return diffSeconds;
    }

    //http://stackoverflow.com/questions/120283/how-can-i-measure-distance-and-create-a-bounding-box-based-on-two-latitudelongi
    public static double distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; // 3958.75 miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }


}
