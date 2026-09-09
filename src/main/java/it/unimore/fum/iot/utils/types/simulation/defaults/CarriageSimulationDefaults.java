package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class CarriageSimulationDefaults implements IBaseSimulationDefaults {

    public final boolean ENABLE_EMERGENCY;
    public final long EMERGENCY_FLOOR_STEP_BOUND;
    public final long EMBARK_PHASE_LENGTH;
    public final long BEGIN_TRAVEL_PHASE_LENGTH;
    public final long TRAVEL_PHASE_LENGTH;
    public final long END_TRAVEL_PHASE_LENGTH;
    public final long DISEMBARK_PHASE_LENGTH;

    public CarriageSimulationDefaults(String configPath) {
        JsonConfigLoader<CarriageSimulationDefaults> loader = new JsonConfigLoader<>(configPath, CarriageSimulationDefaults.class);
        CarriageSimulationDefaults defaults = loader.load();

        this.ENABLE_EMERGENCY = defaults.ENABLE_EMERGENCY;
        this.EMERGENCY_FLOOR_STEP_BOUND = defaults.EMERGENCY_FLOOR_STEP_BOUND;
        this.EMBARK_PHASE_LENGTH = defaults.EMBARK_PHASE_LENGTH;
        this.BEGIN_TRAVEL_PHASE_LENGTH = defaults.BEGIN_TRAVEL_PHASE_LENGTH;
        this.TRAVEL_PHASE_LENGTH = defaults.TRAVEL_PHASE_LENGTH;
        this.END_TRAVEL_PHASE_LENGTH = defaults.END_TRAVEL_PHASE_LENGTH;
        this.DISEMBARK_PHASE_LENGTH = defaults.DISEMBARK_PHASE_LENGTH;
    }
}
