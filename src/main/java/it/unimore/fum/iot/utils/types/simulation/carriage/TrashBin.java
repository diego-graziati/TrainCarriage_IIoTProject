package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.TrashBinDefaults;

public class TrashBin implements ITrashBin, IBatteryCharged {

    private double batteryCharge;
    private boolean isBatteryCutoff;
    private double trashFillPercentage;
    private double internalTrashTemperature;
    public boolean isTrashBinLocked;

    private final TrashBinDefaults defaults;

    private SimulationActuatorDriver<Boolean> trashBinLockActuator;
    private SimulationSensorDriver<Double> internalTrashTemperatureSensor;
    private SimulationSensorDriver<Double> trashFillPercentageSensor;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public TrashBin(String configPath) {
        this.defaults = new TrashBinDefaults(configPath);

        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
        this.isBatteryCutoff = this.defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.trashFillPercentage = 0.0;
        this.internalTrashTemperature = 0.0;
        this.isTrashBinLocked = this.defaults.INITIAL_TRASH_BIN_LOCK_STATUS;
    }

    public SimulationActuatorDriver<Boolean> getTrashBinLockActuator() {
        return trashBinLockActuator;
    }

    public void connectTrashBinLockActuator(SimulationActuatorDriver<Boolean> trashBinLockActuator) {
        this.trashBinLockActuator = trashBinLockActuator;
    }

    public SimulationSensorDriver<Double> getInternalTrashTemperatureSensor() {
        return internalTrashTemperatureSensor;
    }

    public void connectInternalTrashTemperatureSensor(SimulationSensorDriver<Double> internalTrashTemperatureSensor) {
        this.internalTrashTemperatureSensor = internalTrashTemperatureSensor;
    }

    public SimulationSensorDriver<Double> getTrashFillPercentageSensor() {
        return trashFillPercentageSensor;
    }

    public void connectTrashFillPercentageSensor(SimulationSensorDriver<Double> trashFillPercentageSensor) {
        this.trashFillPercentageSensor = trashFillPercentageSensor;
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
