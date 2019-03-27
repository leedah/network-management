package main.java.stayPoints;

import java.sql.Timestamp;

/**
 * Created by sofia on 9/24/15.
 */
public class StayPoint {
    private Double latitude;
    private Double longtitude;
    private Timestamp Tstart;
    private Timestamp Tend;

    public StayPoint(Double latitude, Double longtitude, Timestamp Tstart, Timestamp Tend) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.Tstart = Tstart;
        this.Tend = Tend;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Timestamp getTstart() {
        return Tstart;
    }

    public void setTstart(Timestamp tstart) {
        this.Tstart = tstart;
    }

    public Timestamp getTend() {
        return Tend;
    }

    public void setTend(Timestamp tend) {
        this.Tend = tend;
    }
}
