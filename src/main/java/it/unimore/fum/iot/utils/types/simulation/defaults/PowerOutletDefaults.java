package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class PowerOutletDefaults implements IBaseSimulationDefaults {

    public final boolean INITIAL_POWER_OUTLET_STATUS;
    public final boolean INITIAL_BATTERY_CUTOFF_STATUS;
    public final double INITIAL_BATTERY_CHARGE;

    public final double DISCHARGE_RATE;
    public final double NATURAL_DISCHARGE_RATE;
    public final double CHARGE_RATE;

    public PowerOutletDefaults(String configPath) {
        JsonConfigLoader<PowerOutletDefaults> loader =  new JsonConfigLoader<>(configPath, PowerOutletDefaults.class);
        PowerOutletDefaults defaults = loader.load();

        this.INITIAL_POWER_OUTLET_STATUS = defaults.INITIAL_POWER_OUTLET_STATUS;
        this.INITIAL_BATTERY_CUTOFF_STATUS =  defaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.INITIAL_BATTERY_CHARGE = defaults.INITIAL_BATTERY_CHARGE;
        this.DISCHARGE_RATE = defaults.DISCHARGE_RATE;
        this.NATURAL_DISCHARGE_RATE = defaults.NATURAL_DISCHARGE_RATE;
        this.CHARGE_RATE = defaults.CHARGE_RATE;
    }

    @Override
    public String toString() {
        return "PowerOutletDefaults{" +
                "INITIAL_POWER_OUTLET_STATUS=" + INITIAL_POWER_OUTLET_STATUS +
                ", INITIAL_BATTERY_CHARGE=" + INITIAL_BATTERY_CHARGE +
                ", DISCHARGE_RATE=" + DISCHARGE_RATE +
                ", NATURAL_DISCHARGE_RATE=" + NATURAL_DISCHARGE_RATE +
                ", CHARGE_RATE=" + CHARGE_RATE +
                '}';
    }
}
