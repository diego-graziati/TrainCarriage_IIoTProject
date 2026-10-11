package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class VelocitySensorModel {
    private long timestamp;
    private double velocity;
    private String velocityUnit;

    private ModelStateChangeNotifier listener;

    public VelocitySensorModel(SensorDriver<Double> velocitySensor) {
        this.timestamp = System.currentTimeMillis();
        this.velocity = 0;
        this.velocityUnit = "m/s";

        velocitySensor.registerListeners(val -> {
            if (val < 0) {
                this.velocity = 0;
            } else {
                this.velocity = val;
            }

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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public String getVelocityUnit() {
        return velocityUnit;
    }

    public void setVelocityUnit(String velocityUnit) {
        this.velocityUnit = velocityUnit;
    }

    @Override
    public String toString() {
        return "VelocitySensorModel{" +
                "timestamp=" + timestamp +
                ", velocity=" + velocity + " " + velocityUnit + '\'' +
                '}';
    }
}
