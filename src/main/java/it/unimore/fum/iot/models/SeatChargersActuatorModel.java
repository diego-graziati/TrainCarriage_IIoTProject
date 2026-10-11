package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class SeatChargersActuatorModel {
    private long timestamp;
    private boolean isActive;

    private final ActuatorDriver<Boolean> seatChargerActuator;

    public SeatChargersActuatorModel(ActuatorDriver<Boolean> seatChargerActuator) {
        this.timestamp = System.currentTimeMillis();
        this.isActive = true;

        this.seatChargerActuator = seatChargerActuator;
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
        this.seatChargerActuator.execute(this.isActive);
    }

    @Override
    public String toString() {
        return "SeatChargersActuatorModel{" +
                "timestamp=" + timestamp +
                ", areActive=" + isActive +
                '}';
    }
}
