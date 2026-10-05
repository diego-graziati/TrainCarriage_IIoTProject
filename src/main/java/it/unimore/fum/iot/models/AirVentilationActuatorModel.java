package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class AirVentilationActuatorModel {
    private long timestamp;
    private boolean isOn;

    private final ActuatorDriver<Boolean> onOffAirVentilationActuator;

    public AirVentilationActuatorModel(ActuatorDriver<Boolean> onOffAirVentilationActuator) {
        this.timestamp = System.currentTimeMillis();
        this.isOn = true;
        this.onOffAirVentilationActuator = onOffAirVentilationActuator;
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
        this.onOffAirVentilationActuator.execute(this.isOn);
    }

    @Override
    public String toString() {
        return "AirVentilationActuatorModel{" +
                "timestamp=" + timestamp +
                ", isOpen=" + isOn +
                '}';
    }
}
