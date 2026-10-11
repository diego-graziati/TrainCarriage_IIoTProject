package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

import java.util.Objects;

public class DoorLockActuatorModel {
    private boolean isLocked;
    private final ActuatorDriver<Boolean> doorLockActuator;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public DoorLockActuatorModel(ActuatorDriver<Boolean> doorLockActuator) {
        this.isLocked = true;
        this.doorLockActuator = doorLockActuator;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
        this.doorLockActuator.execute(locked);
    }

    @Override
    public String toString() {
        return "DoorLockActuatorModel{" +
                "isLocked=" + isLocked +
                '}';
    }
}
