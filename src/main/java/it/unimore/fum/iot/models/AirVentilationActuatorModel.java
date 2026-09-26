package it.unimore.fum.iot.models;

public class AirVentilationActuatorModel {
    private long timestamp;
    private boolean isOn;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public AirVentilationActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.isOn = true;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean open) {
        isOn = open;
    }

    @Override
    public String toString() {
        return "AirVentilationActuatorModel{" +
                "timestamp=" + timestamp +
                ", isOpen=" + isOn +
                '}';
    }
}
