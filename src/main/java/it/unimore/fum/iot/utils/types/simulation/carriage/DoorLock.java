package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.DoorLockDefaults;

public class DoorLock implements IDoorLock, IBatteryCharged {

    private boolean isDoorLocked;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private DoorLockDefaults defaults;

    public DoorLock(String configPath) {
        this.defaults = new DoorLockDefaults(configPath);

        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    @Override
    public double getBatteryCharge() {
        return this.batteryCharge;
    }

    @Override
    public void setBatteryCharge(double batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    @Override
    public double getNaturalDischargeRate() {
        return this.defaults.NATURAL_DISCHARGE_RATE;
    }

    @Override
    public double getDischargeRate() {
        return this.defaults.DISCHARGE_RATE;
    }

    @Override
    public double getChargeRate() {
        return this.defaults.CHARGE_RATE;
    }

    @Override
    public boolean isCutoff() {
        return this.isBatteryCutoff;
    }

    @Override
    public void cutoff() {
        this.isBatteryCutoff = true;
    }

    @Override
    public void reconnect() {
        this.isBatteryCutoff = false;
    }

    @Override
    public void lockDoor() {
        this.isDoorLocked = true;
    }

    @Override
    public void unlockDoor() {
        this.isDoorLocked = false;
    }

    @Override
    public boolean isDoorLocked() {
        return this.isDoorLocked;
    }
}
