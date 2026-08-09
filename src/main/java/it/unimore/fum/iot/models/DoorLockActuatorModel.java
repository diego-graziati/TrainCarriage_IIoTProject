package it.unimore.fum.iot.models;

public class DoorLockActuatorModel {
    private boolean isLocked;

    public DoorLockActuatorModel() {}

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public DoorLockActuatorModel(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "DoorLockActuatorModel{" +
                "isLocked=" + isLocked +
                '}';
    }
}
