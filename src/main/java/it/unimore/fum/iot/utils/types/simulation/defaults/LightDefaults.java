package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class LightDefaults implements IBaseSimulationDefaults {

    public final boolean INITIAL_LIGHTS_STATUS;
    public final boolean INITIAL_BATTERY_CUTOFF_STATUS;
    public final double INITIAL_BATTERY_CHARGE;

    public final double DISCHARGE_RATE;
    public final double NATURAL_DISCHARGE_RATE;
    public final double CHARGE_RATE;

    public LightDefaults(String configPath) {
        JsonConfigLoader<LightDefaults> loader = new JsonConfigLoader<>(configPath, LightDefaults.class);
        LightDefaults defaults = loader.load();

        this.INITIAL_BATTERY_CUTOFF_STATUS  = defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.INITIAL_LIGHTS_STATUS = defaults.INITIAL_LIGHTS_STATUS;
        this.INITIAL_BATTERY_CHARGE = defaults.INITIAL_BATTERY_CHARGE;
        this.DISCHARGE_RATE = defaults.DISCHARGE_RATE;
        this.NATURAL_DISCHARGE_RATE = defaults.NATURAL_DISCHARGE_RATE;
        this.CHARGE_RATE = defaults.CHARGE_RATE;
    }
}
