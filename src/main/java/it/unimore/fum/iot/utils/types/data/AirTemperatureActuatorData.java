package it.unimore.fum.iot.utils.types.data;

public class AirTemperatureActuatorData {
    private double targetTemperature;

    public AirTemperatureActuatorData(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
}
