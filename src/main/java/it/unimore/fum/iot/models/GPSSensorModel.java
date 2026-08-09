package it.unimore.fum.iot.models;

public class GPSSensorModel {
    private long timestamp;
    private double longitude;
    private double latitude;

    private String longitudeUnit;
    private String latitudeUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public GPSSensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.longitude = 0;
        this.latitude = 0;
        this.longitudeUnit = "lon";
        this.latitudeUnit = "lat";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLongitudeUnit() {
        return longitudeUnit;
    }

    public void setLongitudeUnit(String longitudeUnit) {
        this.longitudeUnit = longitudeUnit;
    }

    public String getLatitudeUnit() {
        return latitudeUnit;
    }

    public void setLatitudeUnit(String latitudeUnit) {
        this.latitudeUnit = latitudeUnit;
    }

    @Override
    public String toString() {
        return "GPSSensorModel{" +
                "timestamp=" + timestamp +
                ", longitude=" + longitude + " " + longitudeUnit + '\'' +
                ", latitude=" + latitude + " " + latitudeUnit + '\'' +
                '}';
    }
}
