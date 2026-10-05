package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.DoorLockDefaults;

public class DoorLock implements IDoorLock, IBatteryCharged {

    private boolean isDoorLocked;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private final DoorLockDefaults defaults;

    private SimulationActuatorDriver<Boolean> doorLockActuator;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public DoorLock(String configPath) {
        this.defaults = new DoorLockDefaults(configPath);

        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    public void connectDoorLockActuator(SimulationActuatorDriver<Boolean> doorLockActuator) {
        this.doorLockActuator = doorLockActuator;
    }

    public SimulationActuatorDriver<Boolean> getDoorLockActuator() {
        return doorLockActuator;
    }

    public void connectBatteryChargeSensor(SimulationSensorDriver<Double> batteryChargeSensor) {
        this.batteryChargeSensor = batteryChargeSensor;
    }

    public SimulationSensorDriver<Double> getBatteryChargeSensor() {
        return this.batteryChargeSensor;
    }

    public void connectEnergyConsumptionSensor(SimulationSensorDriver<Double> energyConsumptionSensor) {
        this.energyConsumptionSensor = energyConsumptionSensor;
    }

    public SimulationSensorDriver<Double> getEnergyConsumptionSensor() {
        return this.energyConsumptionSensor;
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
