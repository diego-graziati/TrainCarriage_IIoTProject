package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class AirVentilationDefaults implements IBaseSimulationDefaults {

    public final boolean INITIAL_AIR_VENTS_STATUS;
    public final boolean INITIAL_AIR_VENTILATION_STATUS;
    public final boolean INITIAL_DEHUMIDIFIER_STATUS;
    public final double INITIAL_AIR_VENTILATION_MODIFIER;
    public final double INITIAL_DEHUMIDIFIER_MODIFIER;
    public final double AIR_VENTILATION_MODIFIER_BOTTOM_LIMIT;
    public final double AIR_VENTILATION_MODIFIER_TOP_LIMIT;
    public final double DEHUMIDIFIER_MODIFIER_BOTTOM_LIMIT;
    public final double DEHUMIDIFIER_MODIFIER_TOP_LIMIT;
    public final double INITIAL_BATTERY_CHARGE;
    public final double AIR_VENTILATION_EFFICIENCY;
    public final double DEHUMIDIFIER_EFFICIENCY;
    public final double INITIAL_TARGET_AIR_TEMPERATURE;
    public final double INITIAL_TARGET_AIR_HUMIDITY;
    public final double TARGET_AIR_TEMPERATURE_BOTTOM_LIMIT;
    public final double TARGET_AIR_TEMPERATURE_TOP_LIMIT;
    public final double TARGET_AIR_HUMIDITY_BOTTOM_LIMIT;
    public final double TARGET_AIR_HUMIDITY_TOP_LIMIT;

    public final boolean INITIAL_BATTERY_CUTOFF_STATUS;
    public final double DISCHARGE_RATE;
    public final double NATURAL_DISCHARGE_RATE;
    public final double CHARGE_RATE;

    public AirVentilationDefaults(String configPath) {
        JsonConfigLoader<AirVentilationDefaults> loader = new JsonConfigLoader<>(configPath, AirVentilationDefaults.class);
        AirVentilationDefaults defaults = loader.load();

        this.INITIAL_AIR_VENTS_STATUS = defaults.INITIAL_AIR_VENTS_STATUS;
        this.INITIAL_AIR_VENTILATION_STATUS = defaults.INITIAL_AIR_VENTILATION_STATUS;
        this.INITIAL_DEHUMIDIFIER_STATUS = defaults.INITIAL_DEHUMIDIFIER_STATUS;
        this.INITIAL_AIR_VENTILATION_MODIFIER = defaults.INITIAL_AIR_VENTILATION_MODIFIER;
        this.INITIAL_DEHUMIDIFIER_MODIFIER = defaults.INITIAL_DEHUMIDIFIER_MODIFIER;
        this.AIR_VENTILATION_MODIFIER_BOTTOM_LIMIT = defaults.AIR_VENTILATION_MODIFIER_BOTTOM_LIMIT;
        this.AIR_VENTILATION_MODIFIER_TOP_LIMIT = defaults.AIR_VENTILATION_MODIFIER_TOP_LIMIT;
        this.DEHUMIDIFIER_MODIFIER_BOTTOM_LIMIT = defaults.DEHUMIDIFIER_MODIFIER_BOTTOM_LIMIT;
        this.DEHUMIDIFIER_MODIFIER_TOP_LIMIT =  defaults.DEHUMIDIFIER_MODIFIER_TOP_LIMIT;
        this.INITIAL_BATTERY_CHARGE = defaults.INITIAL_BATTERY_CHARGE;
        this.AIR_VENTILATION_EFFICIENCY = defaults.AIR_VENTILATION_EFFICIENCY;
        this.DEHUMIDIFIER_EFFICIENCY = defaults.DEHUMIDIFIER_EFFICIENCY;
        this.INITIAL_TARGET_AIR_TEMPERATURE = defaults.INITIAL_TARGET_AIR_TEMPERATURE;
        this.INITIAL_TARGET_AIR_HUMIDITY = defaults.INITIAL_TARGET_AIR_HUMIDITY;
        this.TARGET_AIR_TEMPERATURE_BOTTOM_LIMIT = defaults.TARGET_AIR_TEMPERATURE_BOTTOM_LIMIT;
        this.TARGET_AIR_TEMPERATURE_TOP_LIMIT = defaults.TARGET_AIR_TEMPERATURE_TOP_LIMIT;
        this.TARGET_AIR_HUMIDITY_BOTTOM_LIMIT = defaults.TARGET_AIR_HUMIDITY_BOTTOM_LIMIT;
        this.TARGET_AIR_HUMIDITY_TOP_LIMIT = defaults.TARGET_AIR_HUMIDITY_TOP_LIMIT;

        this.INITIAL_BATTERY_CUTOFF_STATUS = defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.DISCHARGE_RATE = defaults.DISCHARGE_RATE;
        this.NATURAL_DISCHARGE_RATE = defaults.NATURAL_DISCHARGE_RATE;
        this.CHARGE_RATE = defaults.CHARGE_RATE;
    }
}
