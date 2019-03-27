package main.java.gps;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by sofia on 8/1/15.
 */
@Entity
@Table(name = "GPS", schema = "", catalog = "datasets")
public class Gps {
    private int id;
    private String user;
    private Double latitude;
    private Double longtitude;
    private Timestamp timestamp;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longtitude")
    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gps gps = (Gps) o;

        if (id != gps.id) return false;
        if (latitude != null ? !latitude.equals(gps.latitude) : gps.latitude != null) return false;
        if (longtitude != null ? !longtitude.equals(gps.longtitude) : gps.longtitude != null) return false;
        if (timestamp != null ? !timestamp.equals(gps.timestamp) : gps.timestamp != null) return false;
        if (user != null ? !user.equals(gps.user) : gps.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longtitude != null ? longtitude.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
