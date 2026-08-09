package it.unimore.fum.iot.models;

public class SeatChargersActuatorModel {
    private long timestamp;
    private boolean isActive;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public SeatChargersActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.isActive = true;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "SeatChargersActuatorModel{" +
                "timestamp=" + timestamp +
                ", areActive=" + isActive +
                '}';
    }
}
