package it.unimore.fum.iot.utils.types.data;

public class AirVentilationActuatorData {
    private boolean isOn;

    public AirVentilationActuatorData(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
