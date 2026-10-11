package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.IBatteryCharged;
import it.unimore.fum.iot.utils.types.simulation.defaults.AirVentilationDefaults;

public class AirVentilation implements IAirVentilation, IBatteryCharged {

    private boolean areAirVentsOpen;
    private boolean isAirVentilationOn;
    private double airVentilationModifier;
    private boolean isDehumidifierOn;
    private double dehumidifierModifier;
    private double targetAirTemperature;
    private double targetAirHumidity;
    private double batteryCharge;
    private boolean isBatteryCutoff;

    private final AirVentilationDefaults defaults;

    private SimulationSensorDriver<Double> airTemperatureSensor;
    private SimulationSensorDriver<Double> humiditySensor;
    private SimulationSensorDriver<Double> batteryChargerSensor;
    private SimulationSensorDriver<Double> energyConsumptionSensor;
    private SimulationActuatorDriver<Boolean> airVentilationActuator;
    private SimulationActuatorDriver<Boolean> dehumidifierActuator;
    private SimulationActuatorDriver<Boolean> airVentsActuator;
    private SimulationActuatorDriver<Double> targetAirTemperatureActuator;
    private SimulationActuatorDriver<Double> targetHumidityActuator;

    public AirVentilation(String configPath) {
        this.defaults = new AirVentilationDefaults(configPath);

        this.areAirVentsOpen = this.defaults.INITIAL_AIR_VENTS_STATUS;
        this.isAirVentilationOn = this.defaults.INITIAL_AIR_VENTILATION_STATUS;
        this.isDehumidifierOn = this.defaults.INITIAL_DEHUMIDIFIER_STATUS;
        this.airVentilationModifier = this.defaults.INITIAL_AIR_VENTILATION_MODIFIER;
        this.dehumidifierModifier = this.defaults.INITIAL_DEHUMIDIFIER_MODIFIER;
        this.targetAirTemperature = this.defaults.INITIAL_TARGET_AIR_TEMPERATURE;
        this.targetAirHumidity = this.defaults.INITIAL_TARGET_AIR_HUMIDITY;
        this.batteryCharge = this.defaults.INITIAL_BATTERY_CHARGE;
    }

    public SimulationSensorDriver<Double> getAirTemperatureSensor() {
        return airTemperatureSensor;
    }

    public void connectAirTemperatureSensor(SimulationSensorDriver<Double> airTemperatureSensor) {
        this.airTemperatureSensor = airTemperatureSensor;
    }

    public SimulationSensorDriver<Double> getHumiditySensor() {
        return humiditySensor;
    }

    public void connectHumiditySensor(SimulationSensorDriver<Double> humiditySensor) {
        this.humiditySensor = humiditySensor;
    }

    public SimulationSensorDriver<Double> getBatteryChargerSensor() {
        return batteryChargerSensor;
    }

    public void connectBatteryChargeSensor(SimulationSensorDriver<Double> batteryChargerSensor) {
        this.batteryChargerSensor = batteryChargerSensor;
    }

    public SimulationSensorDriver<Double> getEnergyConsumptionSensor() {
        return energyConsumptionSensor;
    }

    public void connectEnergyConsumptionSensor(SimulationSensorDriver<Double> energyConsumptionSensor) {
        this.energyConsumptionSensor = energyConsumptionSensor;
    }

    public SimulationActuatorDriver<Boolean> getAirVentilationActuator() {
        return airVentilationActuator;
    }

    public void connectAirVentilationActuator(SimulationActuatorDriver<Boolean> airVentilationActuator) {
        this.airVentilationActuator = airVentilationActuator;
    }

    public SimulationActuatorDriver<Boolean> getDehumidifierActuator() {
        return dehumidifierActuator;
    }

    public void connectDehumidifierActuator(SimulationActuatorDriver<Boolean> dehumidifierActuator) {
        this.dehumidifierActuator = dehumidifierActuator;
    }

    public SimulationActuatorDriver<Boolean> getAirVentsActuator() {
        return airVentsActuator;
    }

    public void connectAirVentsActuator(SimulationActuatorDriver<Boolean> airVentsActuator) {
        this.airVentsActuator = airVentsActuator;
    }

    public SimulationActuatorDriver<Double> getTargetAirTemperatureActuator() {
        return targetAirTemperatureActuator;
    }

    public void connectTargetAirTemperatureActuator(SimulationActuatorDriver<Double> targetAirTemperatureActuator) {
        this.targetAirTemperatureActuator = targetAirTemperatureActuator;
    }

    public SimulationActuatorDriver<Double> getTargetHumidityActuator() {
        return targetHumidityActuator;
    }

    public void connectTargetHumidityActuator(SimulationActuatorDriver<Double> targetHumidityActuator) {
        this.targetHumidityActuator = targetHumidityActuator;
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
    public boolean areAirVentsOpen() {
        return this.areAirVentsOpen;
    }

    @Override
    public void openAirVents() {
        this.areAirVentsOpen = true;
    }

    @Override
    public void closeAirVents() {
        this.areAirVentsOpen = false;
    }

    @Override
    public boolean isAirVentilationOn() {
        return this.isAirVentilationOn;
    }

    @Override
    public void turnAirVentilationOn() {
        this.isAirVentilationOn = true;
    }

    @Override
    public void turnAirVentilationOff() {
        this.isAirVentilationOn = false;
    }

    @Override
    public boolean isDehumidifierOn() {
        return this.isDehumidifierOn;
    }

    @Override
    public void turnDehumidifierOn() {
        this.isDehumidifierOn = true;
    }

    @Override
    public void turnDehumidifierOff() {
        this.isDehumidifierOn = false;
    }

    @Override
    public double getAirVentilationModifier() {
        return this.airVentilationModifier;
    }

    @Override
    public void setAirVentilationModifier(double modifier) {
        if (modifier < this.defaults.AIR_VENTILATION_MODIFIER_BOTTOM_LIMIT) {
            this.airVentilationModifier = this.defaults.AIR_VENTILATION_MODIFIER_BOTTOM_LIMIT;
        } else if (modifier > this.defaults.AIR_VENTILATION_MODIFIER_TOP_LIMIT) {
            this.airVentilationModifier = this.defaults.AIR_VENTILATION_MODIFIER_TOP_LIMIT;
        } else {
            this.airVentilationModifier = modifier;
        }
    }

    @Override
    public double getDehumidifierModifier() {
        return this.dehumidifierModifier;
    }

    @Override
    public void setDehumidifierModifier(double modifier) {
        if (modifier < this.defaults.DEHUMIDIFIER_MODIFIER_BOTTOM_LIMIT) {
            this.dehumidifierModifier = this.defaults.DEHUMIDIFIER_MODIFIER_BOTTOM_LIMIT;
        } else if (modifier > this.defaults.DEHUMIDIFIER_MODIFIER_TOP_LIMIT) {
            this.dehumidifierModifier = this.defaults.DEHUMIDIFIER_MODIFIER_TOP_LIMIT;
        } else {
            this.dehumidifierModifier = modifier;
        }
    }

    @Override
    public double getAirVentilationEfficiency() {
        return this.defaults.AIR_VENTILATION_EFFICIENCY;
    }

    @Override
    public double getDehumidifierEfficiency() {
        return this.defaults.DEHUMIDIFIER_EFFICIENCY;
    }

    @Override
    public double getTargetAirTemperature() {
        return this.targetAirTemperature;
    }

    @Override
    public void setTargetAirTemperature(double targetAirTemperature) {
        if (targetAirTemperature <  this.defaults.TARGET_AIR_TEMPERATURE_BOTTOM_LIMIT) {
            this.targetAirTemperature = this.defaults.TARGET_AIR_TEMPERATURE_BOTTOM_LIMIT;
        } else if(targetAirTemperature > this.defaults.TARGET_AIR_TEMPERATURE_TOP_LIMIT) {
            this.targetAirTemperature = this.defaults.TARGET_AIR_TEMPERATURE_TOP_LIMIT;
        } else {
            this.targetAirTemperature = targetAirTemperature;
        }
    }

    @Override
    public double getTargetHumidity() {
        return this.targetAirHumidity;
    }

    @Override
    public void setTargetHumidity(double targetHumidity) {
        if (this.targetAirHumidity < this.defaults.TARGET_AIR_HUMIDITY_BOTTOM_LIMIT) {
            this.targetAirHumidity = this.defaults.TARGET_AIR_HUMIDITY_BOTTOM_LIMIT;
        } else if (this.targetAirHumidity > this.defaults.TARGET_AIR_HUMIDITY_TOP_LIMIT) {
            this.targetAirHumidity = this.defaults.TARGET_AIR_HUMIDITY_TOP_LIMIT;
        } else {
            this.targetAirHumidity = targetHumidity;
        }
    }
}
