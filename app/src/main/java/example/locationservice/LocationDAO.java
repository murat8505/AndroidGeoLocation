package example.locationservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationDAO {

    private double latitude;
    private double longitude;
    private double speed;
    private double altitude;
    private String address;
    private Date date;

    public LocationDAO(double latitude, double longitude, double speed, double altitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.altitude = altitude;
        this.address = address;
        date = new Date();
    }

    public String getLatitude() {
        return "" + latitude;
    }

    public String getLongitude() {
        return "" + longitude;
    }

    public String getSpeed() {
        return "" + speed;
    }

    public String getAltitude() {
        return "" + altitude;
    }

    public String getAddress() { return address; }

    public String getDate() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date);
    }

    @Override
    public String toString() {
        return "LocationDAO{" +
                "lattitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", altitude=" + altitude +
                ", address='" + address + '\'' +
                ", date='" + getDate() + '\'' +
                '}';
    }
}
