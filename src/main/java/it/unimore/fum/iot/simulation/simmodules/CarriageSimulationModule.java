package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.utils.types.simulation.defaults.CarriageSimulationDefaults;
import it.unimore.fum.iot.utils.types.simulation.defaults.IBaseSimulationDefaults;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CarriageSimulationModule extends BaseSimulationModule {

    private final CarriageSimulationDefaults defaults;
    private final int updateFrequency;
    private final ScheduledExecutorService executor;
    private final Logger logger;

    public CarriageSimulationModule(CarriageSimulationDefaults defaults, int updateFrequency) {
        super(defaults, updateFrequency);

        this.logger = Logger.getLogger(CarriageSimulationModule.class.getName());
        this.defaults = defaults;
        this.updateFrequency = updateFrequency;

        this.executor = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void start() {
        this.executor.scheduleAtFixedRate(() -> {
            this.logger.info("Carriage simulation started");
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }
}
