package it.unimore.fum.iot.models;

public class TemperatureSensorModel {
    private long timestamp;
    private double temperature;

    private String temperatureUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public TemperatureSensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.temperature = 10.0;
        this.temperatureUnit = "Cel";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    @Override
    public String toString() {
        return "TemperatureSensorModel{" +
                "timestamp=" + timestamp +
                ", temperature=" + temperature + " " + temperatureUnit +
                '}';
    }
}
