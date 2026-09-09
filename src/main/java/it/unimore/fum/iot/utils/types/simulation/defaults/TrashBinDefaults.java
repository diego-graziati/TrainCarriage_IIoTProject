package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class TrashBinDefaults {

    private final double INITIAL_TRASH_FILL_PERCENTAGE;
    private final double INITIAL_INTERNAL_TRASH_TEMPERATURE;
    public final boolean INITIAL_TRASH_BIN_LOCK_STATUS;
    public final boolean INITIAL_BATTERY_CUTOFF_STATUS;
    public final double INITIAL_BATTERY_CHARGE;

    public final double DISCHARGE_RATE;
    public final double NATURAL_DISCHARGE_RATE;
    public final double CHARGE_RATE;

    public TrashBinDefaults(String configPath) {
        JsonConfigLoader<TrashBinDefaults> loader = new JsonConfigLoader<>(configPath, TrashBinDefaults.class);
        TrashBinDefaults trashBinDefaults = loader.load();

        this.INITIAL_TRASH_FILL_PERCENTAGE = trashBinDefaults.INITIAL_TRASH_FILL_PERCENTAGE;
        this.INITIAL_INTERNAL_TRASH_TEMPERATURE = trashBinDefaults.INITIAL_INTERNAL_TRASH_TEMPERATURE;
        this.INITIAL_TRASH_BIN_LOCK_STATUS = trashBinDefaults.INITIAL_TRASH_BIN_LOCK_STATUS;
        this.INITIAL_BATTERY_CUTOFF_STATUS = trashBinDefaults.INITIAL_BATTERY_CUTOFF_STATUS;
        this.INITIAL_BATTERY_CHARGE = trashBinDefaults.INITIAL_BATTERY_CHARGE;
        this.DISCHARGE_RATE = trashBinDefaults.DISCHARGE_RATE;
        this.NATURAL_DISCHARGE_RATE = trashBinDefaults.NATURAL_DISCHARGE_RATE;
        this.CHARGE_RATE = trashBinDefaults.CHARGE_RATE;
    }
}
