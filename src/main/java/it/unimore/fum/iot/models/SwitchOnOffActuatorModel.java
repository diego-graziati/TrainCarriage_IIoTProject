package it.unimore.fum.iot.models;

public class SwitchOnOffActuatorModel {
    private long timestamp;
    private boolean isOn;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public SwitchOnOffActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.isOn = false;
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

    public void setOn(boolean on) {
        isOn = on;
    }

    @Override
    public String toString() {
        return "SwitchOnOffActuatorModel{" +
                "timestamp=" + timestamp +
                ", isOn=" + isOn +
                '}';
    }
}
