package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.PowerOutletDefaults;

public class PowerOutlet implements IPowerOutlet, IBatteryCharged {

    private boolean isOccupied;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private PowerOutletDefaults defaults;

    public PowerOutlet(String configPath) {
        this.defaults = new PowerOutletDefaults(configPath);

        this.isOccupied = this.defaults.INITIAL_POWER_OUTLET_STATUS;
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    @Override
    public boolean isOccupied() {
        return this.isOccupied;
    }

    @Override
    public void connect() {
        this.isOccupied = true;
    }

    @Override
    public void disconnect() {
        this.isOccupied = false;
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
    public String toString() {
        return "PowerOutlet{" +
                "isOccupied=" + isOccupied +
                ", batteryCharge=" + batteryCharge +
                ", defaults=" + defaults +
                '}';
    }
}
