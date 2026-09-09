package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;

public class LampDefaults implements IBaseSimulationDefaults {

    public final boolean INITIAL_LAMP_STATUS;
    private final BrightnessLevelsEnum INITIAL_BRIGHTNESS;
    public final boolean INITIAL_BATTERY_CUTOFF_STATUS;
    public final double INITIAL_BATTERY_CHARGE;

    public final double DISCHARGE_RATE;
    public final double NATURAL_DISCHARGE_RATE;
    public final double CHARGE_RATE;

    public LampDefaults(String configPath) {
        JsonConfigLoader<LampDefaults> loader = new JsonConfigLoader<>(configPath, LampDefaults.class);
        LampDefaults defaults = loader.load();

        this.INITIAL_BATTERY_CUTOFF_STATUS = defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.INITIAL_LAMP_STATUS = defaults.INITIAL_LAMP_STATUS;
        this.INITIAL_BRIGHTNESS = defaults.INITIAL_BRIGHTNESS;
        this.INITIAL_BATTERY_CHARGE = defaults.INITIAL_BATTERY_CHARGE;
        this.DISCHARGE_RATE = defaults.DISCHARGE_RATE;
        this.NATURAL_DISCHARGE_RATE = defaults.NATURAL_DISCHARGE_RATE;
        this.CHARGE_RATE = defaults.CHARGE_RATE;
    }

    @Override
    public String toString() {
        return "LampDefaults{" +
                "INITIAL_LAMP_STATUS=" + INITIAL_LAMP_STATUS +
                ", INITIAL_BATTERY_CHARGE=" + INITIAL_BATTERY_CHARGE +
                ", DISCHARGE_RATE=" + DISCHARGE_RATE +
                ", NATURAL_DISCHARGE_RATE=" + NATURAL_DISCHARGE_RATE +
                ", CHARGE_RATE=" + CHARGE_RATE +
                '}';
    }
}
