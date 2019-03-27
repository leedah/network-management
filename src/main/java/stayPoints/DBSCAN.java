package main.java.stayPoints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 9/27/15.
 */

//https://github.com/KanwarBhajneek/DBSCAN
public class DBSCAN {
    private static List<StayPoint> visitList = new ArrayList<>();
    private static ArrayList<StayPoint> neighbors = null;

    /** Maximum radius of the neighborhood to be considered. */
    private static double eps;

    /** Minimum number of points needed for a cluster. */
    private static int minPts;

    public DBSCAN(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    public static List<List<StayPoint>> dbscan(List<StayPoint> stayPointList){
        List<List<StayPoint>> result = new ArrayList<>();

        int index2 = 0;

        while (stayPointList.size()>index2){
            StayPoint p =stayPointList.get(index2);
            if(!visitList.contains(p)){

                visitList.add(p);

                neighbors = getNeighbors(p, stayPointList);

                if (neighbors.size()>=minPts){


                    int ind=0;
                    while(neighbors.size()>ind){

                        StayPoint r = neighbors.get(ind);
                        if(!visitList.contains(r)){
                            visitList.add(r);
                            ArrayList<StayPoint> Neighbours2 = getNeighbors(r, stayPointList);
                            if (Neighbours2.size() >= minPts){
                                neighbors = merge(neighbors, Neighbours2);
                            }
                        } ind++;
                    }

//                    System.out.println("N"+neighbors.size());
                    result.add(neighbors);

                }


            }index2++;
        }

        int N = 0;
        for (List<StayPoint> stayPointArrayList: result){


            System.out.println("N"+stayPointArrayList.size());
            if (stayPointArrayList.size() > 1)
                N++;
            for (StayPoint stayPoint: stayPointArrayList){
                System.out.println(stayPoint.getLatitude()+" "+stayPoint.getLongtitude());
            }
            System.out.println("-----------");
        }
        System.out.println(result.size());
        System.out.println(N);

        return result;
    }



    public static ArrayList<StayPoint> getNeighbors(StayPoint stayPoint, List<StayPoint> stayPointList)
    {
        ArrayList<StayPoint> neighbors =new ArrayList<>();

        for (StayPoint sp : stayPointList){
            double distance = StayPointComputation.distance(stayPoint.getLatitude(), stayPoint.getLongtitude(), sp.getLatitude(), sp.getLongtitude());

            if (distance < DBSCAN.eps)
                neighbors.add(sp);
        }

        return neighbors;
    }

    public static ArrayList<StayPoint> merge(ArrayList<StayPoint> stayPoints1, ArrayList<StayPoint> stayPoints2)
    {
        for(StayPoint stayPoint : stayPoints2)
        {
            if(!stayPoints1.contains(stayPoint))
            {
                stayPoints1.add(stayPoint);
            }
        }
        return stayPoints1;
    }

    public static double getEps() {
        return eps;
    }

    public static void setEps(double eps) {
        DBSCAN.eps = eps;
    }

    public static int getMinPts() {
        return minPts;
    }

    public static void setMinPts(int minPts) {
        DBSCAN.minPts = minPts;
    }
}
