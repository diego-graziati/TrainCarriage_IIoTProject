package it.unimore.fum.iot.utils.types.simulation;

public interface IBatteryCharged {
    public double getBatteryCharge();
    public void setBatteryCharge(double batteryCharge);
    public double getNaturalDischargeRate();
    public double getDischargeRate();
    public double getChargeRate();
    public boolean isCutoff();
    public void cutoff();
    public void reconnect();
}
