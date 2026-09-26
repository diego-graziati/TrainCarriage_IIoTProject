package it.unimore.fum.iot.models;

public class AirTemperatureActuatorModel {
    private long timestamp;
    private double targetTemperature;
    private String targetTemperatureUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public AirTemperatureActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.targetTemperature = 30.0;
        this.targetTemperatureUnit = "Cel";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public String getTargetTemperatureUnit() {
        return targetTemperatureUnit;
    }

    public void setTargetTemperatureUnit(String targetTemperatureUnit) {
        this.targetTemperatureUnit = targetTemperatureUnit;
    }

    @Override
    public String toString() {
        return "AirTemperatureActuatorModel{" +
                "timestamp=" + timestamp +
                ", temperature=" + targetTemperature +
                ", temperatureUnit='" + targetTemperatureUnit + '\'' +
                '}';
    }
}
