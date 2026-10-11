package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class AirTemperatureActuatorModel {
    private long timestamp;
    private double targetTemperature;
    private String targetTemperatureUnit;

    private final ActuatorDriver<Double> setTargetTemperatureActuator;

    public AirTemperatureActuatorModel(ActuatorDriver<Double> setTargetTemperatureActuator) {
        this.timestamp = System.currentTimeMillis();
        this.targetTemperature = 30.0;
        this.targetTemperatureUnit = "Cel";
        this.setTargetTemperatureActuator = setTargetTemperatureActuator;
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
        this.setTargetTemperatureActuator.execute(targetTemperature);
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
