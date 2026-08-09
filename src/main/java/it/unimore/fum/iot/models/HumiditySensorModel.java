package it.unimore.fum.iot.models;

public class HumiditySensorModel {
    private long timestamp;
    private double humidity;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public HumiditySensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.humidity = 40.0;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "HumiditySensorModel{" +
                "timestamp=" + timestamp +
                ", humidity=" + humidity +
                '}';
    }
}
