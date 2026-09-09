package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.TrashBinDefaults;

public class TrashBin implements ITrashBin, IBatteryCharged {

    private double batteryCharge;
    private boolean isBatteryCutoff;
    private double trashFillPercentage;
    private double internalTrashTemperature;
    public boolean isTrashBinLocked;

    private final TrashBinDefaults defaults;

    public TrashBin(String configPath) {
        this.defaults = new TrashBinDefaults(configPath);

        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
        this.isBatteryCutoff = this.defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.trashFillPercentage = 0.0;
        this.internalTrashTemperature = 0.0;
        this.isTrashBinLocked = this.defaults.INITIAL_TRASH_BIN_LOCK_STATUS;
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
    public double getFillPercentage() {
        return this.trashFillPercentage;
    }

    @Override
    public void setFillPercentage(double fillPercentage) {
        this.trashFillPercentage = fillPercentage;
    }

    @Override
    public double getInternalTemperature() {
        return this.internalTrashTemperature;
    }

    @Override
    public void setInternalTemperature(double internalTemperature) {
        this.internalTrashTemperature = internalTemperature;
    }

    @Override
    public boolean isOpeningLocked() {
        return this.isTrashBinLocked;
    }

    @Override
    public void lockOpening() {
        this.isTrashBinLocked = true;
    }

    @Override
    public void unlockOpening() {
        this.isTrashBinLocked = false;
    }
}
