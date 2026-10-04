package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.LightDefaults;

public class Light implements ILight, IBatteryCharged {

    private boolean areLightsOn;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private final LightDefaults defaults;

    private SimulationActuatorDriver<Boolean> turnLightsOnOffActuator;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public Light(String configPath) {
        this.defaults = new LightDefaults(configPath);
        this.areLightsOn = this.defaults.INITIAL_LIGHTS_STATUS;
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    public SimulationActuatorDriver<Boolean> getTurnLightsOnOffActuator() {
        return turnLightsOnOffActuator;
    }

    public void connectTurnLightsOnOffActuator(SimulationActuatorDriver<Boolean> turnLightsOnOffActuator) {
        this.turnLightsOnOffActuator = turnLightsOnOffActuator;
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
    public void turnLightsOn() {
        this.areLightsOn = true;
    }

    @Override
    public void turnLightsOff() {
        this.areLightsOn = false;
    }

    @Override
    public boolean isLightsOn() {
        return this.areLightsOn;
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
        return "Light{" +
                "areLightsOn=" + areLightsOn +
                ", batteryCharge=" + batteryCharge +
                ", defaults=" + defaults +
                '}';
    }
}
