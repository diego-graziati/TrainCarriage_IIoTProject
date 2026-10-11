package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class DehumidifierActuatorModel {
    private long timestamp;
    private boolean isOn;

    private final ActuatorDriver<Boolean> onOffDehumidifierActuator;

    public DehumidifierActuatorModel(ActuatorDriver<Boolean> onOffDehumidifierActuator) {
        this.timestamp = System.currentTimeMillis();
        this.isOn = false;
        this.onOffDehumidifierActuator = onOffDehumidifierActuator;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        this.onOffDehumidifierActuator.execute(isOn);
    }

    @Override
    public String toString() {
        return "DehumidifierActuatorModel{" +
                "timestamp=" + timestamp +
                '}';
    }
}
