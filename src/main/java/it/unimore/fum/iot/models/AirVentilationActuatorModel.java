package it.unimore.fum.iot.models;

public class AirVentilationActuatorModel {
    private long timestamp;
    private boolean isOpen;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public AirVentilationActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.isOpen = true;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "AirVentilationActuatorModel{" +
                "timestamp=" + timestamp +
                ", isOpen=" + isOpen +
                '}';
    }
}
