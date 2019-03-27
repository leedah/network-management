package main.java.stayPoints;

import main.java.gps.Gps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 9/24/15.
 */
public class StayPointUtil {

    public static List<StayPoint> getStayPointsFromUser(List<Gps> pointsList, long Tmin,long Tmax,double Dmax){
        List<StayPoint> stayPointList = new ArrayList<>();
        int N = pointsList.size() - 1;

        int j, i = 0;

        while (i < N){
            j = i + 1;

            if (j == N)
                break;

            while (j <= N){

                Gps pj = pointsList.get(j);
                Gps pi = pointsList.get(i);
                Gps pj_1 = pointsList.get(j - 1);

                if (StayPointComputation.timeDiff(pj.getTimestamp(), pj_1.getTimestamp()) > Tmax){
                    if (StayPointComputation.timeDiff(pi.getTimestamp(), pj_1.getTimestamp()) > Tmin){
                        stayPointList.add(StayPointComputation.estimateStayPoint(pointsList.subList(i, j-1)));
                    }
                    i=j;
                    break;
                }

                else if (StayPointComputation.distance(pi.getLatitude(), pi.getLongtitude(), pj.getLatitude(), pj.getLongtitude()) > Dmax){
                    if (StayPointComputation.timeDiff(pi.getTimestamp(), pj_1.getTimestamp()) > Tmin){
                        stayPointList.add(StayPointComputation.estimateStayPoint(pointsList.subList(i, j-1)));
                        i=j;
                        break;
                    }
                    i++;
                    break;
                }

                else if (j == N){
                    if (StayPointComputation.timeDiff(pi.getTimestamp(), pj.getTimestamp()) > Tmin) {
                        stayPointList.add(StayPointComputation.estimateStayPoint(pointsList.subList(i, j)));
                    }
                    i=j;
                    break;
                }
                j++;

            }
        }

        return stayPointList;
    }

}
