package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class SwitchOnOffActuatorModel {
    private long timestamp;
    private boolean isOn;

    private final ActuatorDriver<Boolean> lightOnOffActuator;

    public SwitchOnOffActuatorModel(ActuatorDriver<Boolean> lightOnOffActuator) {
        this.timestamp = System.currentTimeMillis();
        this.isOn = false;

        this.lightOnOffActuator = lightOnOffActuator;
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
        this.lightOnOffActuator.execute(this.isOn);
    }

    @Override
    public String toString() {
        return "SwitchOnOffActuatorModel{" +
                "timestamp=" + timestamp +
                ", isOn=" + isOn +
                '}';
    }
}
