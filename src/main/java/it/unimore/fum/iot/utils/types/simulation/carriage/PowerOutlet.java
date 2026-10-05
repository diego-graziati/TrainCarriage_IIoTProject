package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.PowerOutletDefaults;

public class PowerOutlet implements IPowerOutlet, IBatteryCharged {

    private boolean isOccupied;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private final PowerOutletDefaults defaults;

    private SimulationActuatorDriver<Boolean> cutoffPowerOutletActuator;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public PowerOutlet(String configPath) {
        this.defaults = new PowerOutletDefaults(configPath);

        this.isOccupied = this.defaults.INITIAL_POWER_OUTLET_STATUS;
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    public SimulationActuatorDriver<Boolean> getCutoffPowerOutletActuator() {
        return cutoffPowerOutletActuator;
    }

    public void connectCutoffPowerOutletActuator(SimulationActuatorDriver<Boolean> cutoffPowerOutletActuator) {
        this.cutoffPowerOutletActuator = cutoffPowerOutletActuator;
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
    public void repair() {
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
