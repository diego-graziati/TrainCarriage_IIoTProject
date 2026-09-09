package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class CarriageDefaults implements IBaseSimulationDefaults {

    public final int NUM_EXTERNAL_DOORS;
    public final int NUM_INTERNAL_DOORS;
    public final int NUM_TOILETS;
    public final int NUM_LIGHTS;
    public final int NUM_SEATS;
    public final int NUM_TRASH_BINS;

    public CarriageDefaults(String configPath) {
        JsonConfigLoader<CarriageDefaults> loader = new JsonConfigLoader<>(configPath, CarriageDefaults.class);
        CarriageDefaults defaults = loader.load();

        this.NUM_EXTERNAL_DOORS = defaults.NUM_EXTERNAL_DOORS;
        this.NUM_INTERNAL_DOORS = defaults.NUM_INTERNAL_DOORS;
        this.NUM_TOILETS = defaults.NUM_TOILETS;
        this.NUM_LIGHTS = defaults.NUM_LIGHTS;
        this.NUM_SEATS = defaults.NUM_SEATS;
        this.NUM_TRASH_BINS = defaults.NUM_TRASH_BINS;
    }
}
