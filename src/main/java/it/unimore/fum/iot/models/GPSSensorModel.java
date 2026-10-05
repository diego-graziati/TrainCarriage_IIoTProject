package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.javatuples.Pair;

public class GPSSensorModel {
    private long timestamp;
    private double longitude;
    private double latitude;

    private String longitudeUnit;
    private String latitudeUnit;

    private ModelStateChangeNotifier listener;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public GPSSensorModel(SensorDriver<Pair<Double, Double>> gpsSensor) {
        this.timestamp = System.currentTimeMillis();
        this.longitude = 0;
        this.latitude = 0;
        this.longitudeUnit = "lon";
        this.latitudeUnit = "lat";

        gpsSensor.registerListeners(val -> {
            this.longitude = val.getValue0();
            this.latitude = val.getValue1();

            if (listener != null) {
                this.listener.onStateChange();
            }
        });
    }

    public void setOnStateChange(ModelStateChangeNotifier listener) {
        this.listener = listener;
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
