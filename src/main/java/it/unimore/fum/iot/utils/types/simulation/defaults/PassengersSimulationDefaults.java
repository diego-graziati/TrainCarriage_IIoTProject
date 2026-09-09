package it.unimore.fum.iot.utils.types.simulation.defaults;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class PassengersSimulationDefaults implements IBaseSimulationDefaults {

    public final int MAX_NUMBER_OF_ONBOARD_PASSENGERS;

    public PassengersSimulationDefaults(String configPath) {
        JsonConfigLoader<PassengersSimulationDefaults> loader = new JsonConfigLoader<>(configPath, PassengersSimulationDefaults.class);
        PassengersSimulationDefaults defaults = loader.load();

        this.MAX_NUMBER_OF_ONBOARD_PASSENGERS = defaults.MAX_NUMBER_OF_ONBOARD_PASSENGERS;
    }
}
