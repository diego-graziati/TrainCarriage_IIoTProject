package it.unimore.fum.iot.utils.types.simulation;

public interface IBatteryCharged {
    double getBatteryCharge();
    void setBatteryCharge(double batteryCharge);
    double getNaturalDischargeRate();
    double getDischargeRate();
    double getChargeRate();
    boolean isCutoff();
    void cutoff();
    void repair();
}
