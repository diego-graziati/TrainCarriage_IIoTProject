package it.unimore.fum.iot;

import it.unimore.fum.iot.simulation.SimulationOrchestrator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Logger MAIN_LOGGER;
    public static Logger PASSENGERS_SIM_LOGGER;
    public static Logger TEMPERATURE_SIM_LOGGER;
    public static Logger CARRIAGE_SIM_LOGGER;

    public static void main(String[] args) {
        MAIN_LOGGER = Logger.getLogger("Main-Logger");
        PASSENGERS_SIM_LOGGER = Logger.getLogger("Passenger-Sim-Logger");
        TEMPERATURE_SIM_LOGGER = Logger.getLogger("Temperature-Sim-Logger");
        CARRIAGE_SIM_LOGGER = Logger.getLogger("Carriage-Sim-Logger");

        try {
            FileHandler fileHandler = new FileHandler("logs/main-log.log");
            MAIN_LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            MAIN_LOGGER.info("Main Logger started successfully");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileHandler fileHandler = new FileHandler("logs/passengers-sim-log.log");
            PASSENGERS_SIM_LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            PASSENGERS_SIM_LOGGER.info("Passenger Sim Logger started successfully");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileHandler fileHandler = new FileHandler("logs/carriage-sim-log.log");
            CARRIAGE_SIM_LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            CARRIAGE_SIM_LOGGER.info("Carriage Sim Logger started successfully");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        try {
            FileHandler fileHandler = new FileHandler("logs/temperature-sim-log.log");
            TEMPERATURE_SIM_LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            TEMPERATURE_SIM_LOGGER.info("Logger started successfully");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        MAIN_LOGGER.info("Starting Simulation");
        SimulationOrchestrator simulationOrchestrator = new SimulationOrchestrator();
        simulationOrchestrator.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            MAIN_LOGGER.info("Shutting down Simulation");
            simulationOrchestrator.stop();
        }));
    }


}