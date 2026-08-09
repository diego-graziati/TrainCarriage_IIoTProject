package it.unimore.fum.iot.models;

public class DehumidifierActuatorModel {
    private long timestamp;
    private double humidity;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public DehumidifierActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.humidity = 20.0;
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
        return "DehumidifierActuatorModel{" +
                "timestamp=" + timestamp +
                ", humidity=" + humidity +
                '}';
    }
}
