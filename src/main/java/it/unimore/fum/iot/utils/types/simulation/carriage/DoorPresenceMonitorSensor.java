package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.IPresenceMonitoring;
import it.unimore.fum.iot.utils.types.simulation.defaults.DoorPresenceMonitorSensorDefaults;

public class DoorPresenceMonitorSensor implements IPresenceMonitoring, IBatteryCharged {

    private int in;
    private int out;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private DoorPresenceMonitorSensorDefaults defaults;

    public DoorPresenceMonitorSensor(String configPath) {
        this.defaults = new  DoorPresenceMonitorSensorDefaults(configPath);

        this.in = 0;
        this.out = 0;
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
    public int getIn() {
        return this.in;
    }

    @Override
    public int getOut() {
        return this.out;
    }

    @Override
    public void incrementIn() {
        this.in++;
    }

    @Override
    public void incrementOut() {
        this.out++;
    }
}
