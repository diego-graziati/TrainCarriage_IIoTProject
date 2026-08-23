package it.unimore.fum.iot.utils.types.simulation.defaults;

public class PassengersSimulationDefaults implements IBaseSimulationDefaults {

    public final int NUM_CARRIAGE_SEATINGS;
    public final boolean ARE_DOORS_LOCKED_BY_DEFAULT;
    public final int NUM_DOORS;

    public PassengersSimulationDefaults(int NUM_CARRIAGE_SEATINGS, boolean ARE_DOORS_LOCKED_BY_DEFAULT, int NUM_DOORS) {
        this.NUM_CARRIAGE_SEATINGS = NUM_CARRIAGE_SEATINGS;
        this.ARE_DOORS_LOCKED_BY_DEFAULT = ARE_DOORS_LOCKED_BY_DEFAULT;
        this.NUM_DOORS = NUM_DOORS;
    }
}
