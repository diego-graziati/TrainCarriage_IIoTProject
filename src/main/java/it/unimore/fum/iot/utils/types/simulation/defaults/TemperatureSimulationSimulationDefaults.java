package it.unimore.fum.iot.utils.types.simulation.defaults;

public class TemperatureSimulationSimulationDefaults implements IBaseSimulationDefaults {

    public final double INITIAL_TEMPERATURE;
    public final double EXTERIORS_TEMPERATURE;
    public final int NUM_EXTERIOR_DOORS;
    public final double DOOR_OPEN_TEMPERATURE_MODIFIER;
    public final boolean ARE_DOORS_CLOSED_BY_DEFAULT;

    public TemperatureSimulationSimulationDefaults(double INITIAL_TEMPERATURE,
                                                   double EXTERIORS_TEMPERATURE, double DOOR_OPEN_TEMPERATURE_MODIFIER, boolean ARE_DOORS_CLOSED_BY_DEFAULT,
                                                   int NUM_EXTERIOR_DOORS) {
        this.INITIAL_TEMPERATURE = INITIAL_TEMPERATURE;
        this.EXTERIORS_TEMPERATURE = EXTERIORS_TEMPERATURE;
        this.DOOR_OPEN_TEMPERATURE_MODIFIER = DOOR_OPEN_TEMPERATURE_MODIFIER;
        this.ARE_DOORS_CLOSED_BY_DEFAULT = ARE_DOORS_CLOSED_BY_DEFAULT;
        this.NUM_EXTERIOR_DOORS = NUM_EXTERIOR_DOORS;
    }

    @Override
    public String toString() {
        return "TemperatureSimulationSimulationDefaults{" +
                "INITIAL_TEMPERATURE=" + INITIAL_TEMPERATURE +
                ", EXTERIORS_TEMPERATURE=" + EXTERIORS_TEMPERATURE +
                ", NUM_DOORS=" + NUM_EXTERIOR_DOORS +
                ", DOOR_OPEN_TEMPERATURE_MODIFIER=" + DOOR_OPEN_TEMPERATURE_MODIFIER +
                ", ARE_DOORS_CLOSED_BY_DEFAULT=" + ARE_DOORS_CLOSED_BY_DEFAULT +
                '}';
    }
}

