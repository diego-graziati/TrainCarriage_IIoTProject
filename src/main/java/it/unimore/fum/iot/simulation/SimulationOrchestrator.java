package it.unimore.fum.iot.simulation;

import it.unimore.fum.iot.simulation.simmodules.PassengersSimulationModule;
import it.unimore.fum.iot.simulation.simmodules.TemperatureSimulationModule;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;
import it.unimore.fum.iot.utils.types.simulation.defaults.PassengersSimulationDefaults;
import it.unimore.fum.iot.utils.types.simulation.defaults.TemperatureSimulationSimulationDefaults;

import java.util.logging.Logger;

public class SimulationOrchestrator {

    private final TemperatureSimulationModule temperatureSimulationModule;
    private final PassengersSimulationModule passengersSimulationModule;
    private final Logger logger;

    public SimulationOrchestrator() {
        this.logger = Logger.getLogger(SimulationOrchestrator.class.getName());

        //TESTING IF SIMULATION IS WORKING
        logger.warning("Simulation testing! These won't be the final outputs");
        logger.info("Simulations initialization");
        int updateFrequency = 60;
        int numCarriageSeats = 52;
        int numCarriageToilets = 2;

        TemperatureSimulationSimulationDefaults tempDefault = new TemperatureSimulationSimulationDefaults(
                30.0, 40.0, 0.20,
                true, 3
        );
        this.temperatureSimulationModule = new TemperatureSimulationModule(tempDefault,  updateFrequency);

        Carriage carriage = new Carriage(numCarriageSeats, numCarriageToilets);
        PassengersSimulationDefaults passengersDefault = new PassengersSimulationDefaults(
                numCarriageSeats, true, 3
        );
        this.passengersSimulationModule = new PassengersSimulationModule(passengersDefault, carriage, updateFrequency);

        logger.info("All simulations have been successfully initialized");
    }

    public void start() {
        this.logger.info("Starting simulations");
        this.temperatureSimulationModule.start();
        this.passengersSimulationModule.start();
        this.logger.info("All simulations have been successfully started");
    }

    public void stop() {
        this.logger.info("Stopping simulations");
        this.temperatureSimulationModule.stop();
        this.passengersSimulationModule.stop();
        this.logger.info("All simulations have been successfully stopped");
    }
}
