package it.unimore.fum.iot.models;

public class BatteryChargeSensorModel {
    private long timestamp;
    private double batteryCharge;
    private final double maxCharge;

    private String batteryChargeUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public BatteryChargeSensorModel() {
        this.maxCharge = 100;
        this.batteryCharge = this.maxCharge;
        this.batteryChargeUnit = "mA";
        this.timestamp = System.currentTimeMillis();
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
