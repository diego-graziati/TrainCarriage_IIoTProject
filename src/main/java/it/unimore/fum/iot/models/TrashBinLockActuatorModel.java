package it.unimore.fum.iot.models;

public class TrashBinLockActuatorModel {
    private long timestamp;
    private boolean isLocked;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public TrashBinLockActuatorModel() {
        this.timestamp = System.currentTimeMillis();
        this.isLocked = false;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "TrashBinLockActuatorModel{" +
                "timestamp=" + timestamp +
                ", isLocked=" + isLocked +
                '}';
    }
}
