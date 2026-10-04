package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.LampDefaults;

public class Lamp implements ILamp, IBatteryCharged {

    private boolean isLampOn;
    private BrightnessLevelsEnum brightness;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private final LampDefaults defaults;

    private SimulationActuatorDriver<BrightnessLevelsEnum> brightnessActuator;
    private SimulationActuatorDriver<Boolean> turnOnOffActuator;
    private SimulationSensorDriver<Double> batteryChargeSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;

    public Lamp(String configPath) {
        this.defaults = new LampDefaults(configPath);

        this.isLampOn = this.defaults.INITIAL_LAMP_STATUS;
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    public SimulationActuatorDriver<BrightnessLevelsEnum> getBrightnessActuator() {
        return brightnessActuator;
    }

    public void connectBrightnessActuator(SimulationActuatorDriver<BrightnessLevelsEnum> brightnessActuator) {
        this.brightnessActuator = brightnessActuator;
    }

    public SimulationActuatorDriver<Boolean> getTurnOnOffActuator() {
        return turnOnOffActuator;
    }

    public void connectTurnOnOffActuator(SimulationActuatorDriver<Boolean> turnOnOffActuator) {
        this.turnOnOffActuator = turnOnOffActuator;
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
    public boolean isOn() {
        return this.isLampOn;
    }

    @Override
    public void turnOn() {
        this.isLampOn = true;
    }

    @Override
    public void turnOff() {
        this.isLampOn = false;
    }

    @Override
    public BrightnessLevelsEnum getBrightness() {
        return this.brightness;
    }

    @Override
    public void setBrightness(BrightnessLevelsEnum brightness) {
        this.brightness = brightness;
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
        return "Lamp{" +
                "isLampOn=" + isLampOn +
                ", batteryCharge=" + batteryCharge +
                ", defaults=" + defaults +
                '}';
    }
}
