package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.utils.types.simulation.defaults.IBaseSimulationDefaults;

public abstract class BaseSimulationModule implements IBaseSimulationModule {

    protected int updateFrequency;

    public BaseSimulationModule(IBaseSimulationDefaults defaults, int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public abstract void start();
    public abstract void stop();
}
