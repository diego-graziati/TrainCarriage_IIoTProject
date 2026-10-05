package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class BatteryChargeSensorModel {
    private long timestamp;
    private double batteryCharge;
    private final double maxCharge;

    private String batteryChargeUnit;
    private final SensorDriver<Double> batteryChargeSensor;
    private ModelStateChangeNotifier listener;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public BatteryChargeSensorModel(SensorDriver<Double> batteryChargeSensor) {
        this.maxCharge = 100;
        this.batteryCharge = this.maxCharge;
        this.batteryChargeUnit = "mA";
        this.timestamp = System.currentTimeMillis();

        this.batteryChargeSensor = batteryChargeSensor;

        batteryChargeSensor.registerListeners(val -> {
            if (val > maxCharge) {
                this.batteryCharge = maxCharge;
            } else if (val < 0) {
                this.batteryCharge = 0;
            } else {
                this.batteryCharge = val;
            }

            if (this.listener != null) {
                this.listener.onStateChange();
            }
        });
    }

    public void setOnStateChange(ModelStateChangeNotifier listener) {
        this.listener = listener;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(double batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public String getBatteryChargeUnit() {
        return batteryChargeUnit;
    }

    public void setBatteryChargeUnit(String batteryChargeUnit) {
        this.batteryChargeUnit = batteryChargeUnit;
    }

    @Override
    public String toString() {
        return "BatteryChargeSensorModel{" +
                "timestamp=" + timestamp +
                ", batteryCharge=" + batteryCharge + " " + batteryChargeUnit +
                '}';
    }
}
