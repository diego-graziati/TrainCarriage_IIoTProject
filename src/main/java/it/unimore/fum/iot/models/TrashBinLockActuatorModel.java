package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class TrashBinLockActuatorModel {
    private long timestamp;
    private boolean isLocked;

    private final ActuatorDriver<Boolean> trashBinLockActuator;

    public TrashBinLockActuatorModel(ActuatorDriver<Boolean> trashBinLockActuator) {
        this.timestamp = System.currentTimeMillis();
        this.isLocked = false;
        this.trashBinLockActuator = trashBinLockActuator;
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
        this.trashBinLockActuator.execute(locked);
    }

    @Override
    public String toString() {
        return "TrashBinLockActuatorModel{" +
                "timestamp=" + timestamp +
                ", isLocked=" + isLocked +
                '}';
    }
}
