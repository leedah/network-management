package main.java.accessPoints;

/**
 * Created by sofia on 8/4/15.
 */
public class AccessPointsData {
    private Coordinates coordinates;
    private int rssi;

    public AccessPointsData(Coordinates coordinates, int rssi) {
        this.coordinates = coordinates;
        this.rssi = rssi;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
