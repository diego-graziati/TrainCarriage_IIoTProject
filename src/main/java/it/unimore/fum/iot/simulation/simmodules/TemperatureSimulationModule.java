package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.simulation.SimulationOrchestrator;
import it.unimore.fum.iot.simulation.buffers.BufferWriter;
import it.unimore.fum.iot.simulation.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.simulation.defaults.TemperatureSimulationSimulationDefaults;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class TemperatureSimulationModule extends BaseSimulationModule {

    private final Logger logger;

    private double temperature;
    private final ScheduledExecutorService scheduler;
    private final TemperatureSimulationSimulationDefaults defaults;
    private HashMap<String, Boolean> doorsStates;
    private double airVentilationTemperatureModifier;

    private final BufferWriter<Double> bufferWriter;

    public TemperatureSimulationModule(TemperatureSimulationSimulationDefaults defaults, int updateFrequency) {
        super(defaults, updateFrequency);

        this.logger = Logger.getLogger(SimulationOrchestrator.class.getName());

        this.defaults = defaults;
        this.temperature = defaults.INITIAL_TEMPERATURE;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.doorsStates = new HashMap<>(defaults.NUM_EXTERIOR_DOORS);
        this.airVentilationTemperatureModifier = 0;

        this.bufferWriter = new BufferWriter<>(new SingleItemReadWriteBuffer<>());
    }

    @Override
    public void start() {
        this.scheduler.scheduleAtFixedRate(() -> {
            AtomicInteger numDoorsOpen = new AtomicInteger();
            doorsStates.forEach((doorsId, doorsState) -> {
                if(doorsState) {
                    numDoorsOpen.addAndGet(1);
                }
            });

            double doorsOpenModifier = 0;
            if (this.defaults.EXTERIORS_TEMPERATURE > this.temperature) {
                doorsOpenModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER;
            } else if (this.defaults.EXTERIORS_TEMPERATURE < this.temperature) {
                doorsOpenModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER * (-1);
            }

            this.temperature = this.temperature + this.airVentilationTemperatureModifier + doorsOpenModifier;

            this.logger.info("New temperature: " + this.temperature);

            this.bufferWriter.write(this.temperature);

            this.logger.info("Actual written value inside buffer: " + this.bufferWriter);
        }, 0, 60/super.updateFrequency, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }

    public void updateDoorsStates (HashMap<String, Boolean> doorsStates) {
        this.doorsStates = doorsStates;
    }

    public void updateAirVentilationTemperatureModifier(double airVentilationTemperatureModifier) {
        this.airVentilationTemperatureModifier = airVentilationTemperatureModifier;
    }

    @Override
    public String toString() {
        return "TemperatureSimulationModule{" +
                "temperature=" + temperature +
                ", scheduler=" + scheduler +
                ", defaults=" + defaults +
                ", doorsStates=" + doorsStates +
                ", airVentilationTemperatureModifier=" + airVentilationTemperatureModifier +
                ", simulationRate=" + updateFrequency +
                '}';
    }
}
