package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class TemperatureSimulationDefaults implements IBaseSimulationDefaults {

    public final double INITIAL_TEMPERATURE;
    public final double EXTERIORS_TEMPERATURE;
    public final double INITIAL_HUMIDITY;
    public final double EXTERIORS_HUMIDITY;
    public final int NUM_EXTERIOR_DOORS;
    public final double DOOR_OPEN_TEMPERATURE_MODIFIER;
    public final double DOOR_OPEN_HUMIDITY_MODIFIER;
    public final boolean ARE_DOORS_CLOSED_BY_DEFAULT;

    public TemperatureSimulationDefaults(String configPath) {
        JsonConfigLoader<TemperatureSimulationDefaults> loader = new JsonConfigLoader<>(configPath, TemperatureSimulationDefaults.class);
        TemperatureSimulationDefaults defaults = loader.load();

        this.INITIAL_TEMPERATURE = defaults.INITIAL_TEMPERATURE;
        this.EXTERIORS_TEMPERATURE = defaults.EXTERIORS_TEMPERATURE;
        this.INITIAL_HUMIDITY = defaults.INITIAL_HUMIDITY;
        this.EXTERIORS_HUMIDITY = defaults.EXTERIORS_HUMIDITY;
        this.DOOR_OPEN_TEMPERATURE_MODIFIER = defaults.DOOR_OPEN_TEMPERATURE_MODIFIER;
        this.DOOR_OPEN_HUMIDITY_MODIFIER = defaults.DOOR_OPEN_HUMIDITY_MODIFIER;
        this.ARE_DOORS_CLOSED_BY_DEFAULT = defaults.ARE_DOORS_CLOSED_BY_DEFAULT;
        this.NUM_EXTERIOR_DOORS = defaults.NUM_EXTERIOR_DOORS;
    }

    @Override
    public String toString() {
        return "TemperatureSimulationDefaults{" +
                "INITIAL_TEMPERATURE=" + INITIAL_TEMPERATURE +
                ", EXTERIORS_TEMPERATURE=" + EXTERIORS_TEMPERATURE +
                ", NUM_DOORS=" + NUM_EXTERIOR_DOORS +
                ", DOOR_OPEN_TEMPERATURE_MODIFIER=" + DOOR_OPEN_TEMPERATURE_MODIFIER +
                ", ARE_DOORS_CLOSED_BY_DEFAULT=" + ARE_DOORS_CLOSED_BY_DEFAULT +
                '}';
    }
}

