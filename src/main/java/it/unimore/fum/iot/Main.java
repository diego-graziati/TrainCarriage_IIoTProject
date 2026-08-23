package it.unimore.fum.iot;

import it.unimore.fum.iot.simulation.SimulationOrchestrator;

import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        logger.info("Starting Simulation");
        SimulationOrchestrator simulationOrchestrator = new SimulationOrchestrator();
        simulationOrchestrator.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down Simulation");
            simulationOrchestrator.stop();
        }));
    }


}