package it.unimore.fum.iot.simulation;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.simulation.simmodules.CarriageSimulationModule;
import it.unimore.fum.iot.simulation.simmodules.PassengersSimulationModule;
import it.unimore.fum.iot.simulation.simmodules.TemperatureHumiditySimulationModule;
import it.unimore.fum.iot.utils.types.collectors.CarriageBuffersCollector;
import it.unimore.fum.iot.utils.types.collectors.TemperatureHumidityBuffersCollector;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;

public class SimulationOrchestrator {

    private final TemperatureHumiditySimulationModule temperatureHumiditySimulationModule;
    private final PassengersSimulationModule passengersSimulationModule;
    private final CarriageSimulationModule carriageSimulationModule;

    public SimulationOrchestrator() {
        //TESTING IF SIMULATION IS WORKING
        Main.MAIN_LOGGER.info("Simulations initialization");
        int updateFrequency = 60;

        Carriage carriage = new Carriage();
        CarriageBuffersCollector carriageBuffersCollector = new CarriageBuffersCollector(carriage);
        TemperatureHumidityBuffersCollector temperatureHumidityBuffersCollector = new TemperatureHumidityBuffersCollector();
        this.temperatureHumiditySimulationModule = new TemperatureHumiditySimulationModule(carriage,
                temperatureHumidityBuffersCollector, updateFrequency);
        this.passengersSimulationModule = new PassengersSimulationModule(carriage, updateFrequency);
        this.carriageSimulationModule = new CarriageSimulationModule(carriage, carriageBuffersCollector, updateFrequency);

        Main.MAIN_LOGGER.info("All simulations have been successfully initialized");
    }

    public void start() {
        Main.MAIN_LOGGER.info("Starting simulations");
        this.temperatureHumiditySimulationModule.start();
        this.passengersSimulationModule.start();
        this.carriageSimulationModule.start();
        Main.MAIN_LOGGER.info("All simulations have been successfully started");
    }

    public void stop() {
        Main.MAIN_LOGGER.info("Stopping simulations");
        this.temperatureHumiditySimulationModule.stop();
        this.passengersSimulationModule.stop();
        this.carriageSimulationModule.stop();
        Main.MAIN_LOGGER.info("All simulations have been successfully stopped");
    }
}
