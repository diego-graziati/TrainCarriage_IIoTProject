package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.*;
import it.unimore.fum.iot.utils.types.simulation.defaults.DoorDefaults;

public class Door implements IDoor, IBatteryCharged, ILockableDoor, IPresenceMonitoringDoor {

    private boolean isDoorOpen;
    private double batteryCharge;
    private boolean isBatteryCutoff;
    private final DoorLock lock;
    private final DoorPresenceMonitorSensor presenceMonitor;

    private final DoorDefaults defaults;

    private SimulationSensorDriver<Boolean> doorOpenSensor;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public Door (String configPath) {
        this.defaults = new DoorDefaults(configPath);
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
        this.isDoorOpen = this.defaults.INITIAL_DOOR_STATUS;
        this.lock = new DoorLock(Paths.Config.Carriage.Door.LOCK);
        this.presenceMonitor = new DoorPresenceMonitorSensor(Paths.Config.Carriage.Door.PRESENCE_MONITOR);
    }

    public SimulationSensorDriver<Boolean> getDoorOpenSensor() {
        return doorOpenSensor;
    }

    public void connectDoorOpenSensor(SimulationSensorDriver<Boolean> doorOpenSensor) {
        this.doorOpenSensor = doorOpenSensor;
    }

    public SimulationSensorDriver<Double> getBatteryChargeSensor() {
        return batteryChargeSensor;
    }

    public void connectBatteryChargeSensor(SimulationSensorDriver<Double> batteryChargeSensor) {
        this.batteryChargeSensor = batteryChargeSensor;
    }

    public SimulationSensorDriver<Double> getEnergyConsumptionSensor() {
        return energyConsumptionSensor;
    }

    public void connectEnergyConsumptionSensor(SimulationSensorDriver<Double> energyConsumptionSensor) {
        this.energyConsumptionSensor = energyConsumptionSensor;
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
    public void repair() {
        this.isBatteryCutoff = false;
    }

    @Override
    public void openDoor() {
        this.isDoorOpen = true;
    }

    @Override
    public void closeDoor() {
        this.isDoorOpen = false;
    }

    @Override
    public boolean isDoorOpen() {
        return this.isDoorOpen;
    }

    @Override
    public DoorLock getDoorLock() {
        return this.lock;
    }

    @Override
    public DoorPresenceMonitorSensor getPresenceMonitor() {
        return this.presenceMonitor;
    }
}
