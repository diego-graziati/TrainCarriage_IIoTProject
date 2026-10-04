package it.unimore.fum.iot;

import it.unimore.fum.iot.simulation.SimulationOrchestrator;
import it.unimore.fum.iot.utils.types.drivers.SimulationActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SimulationSensorDriver;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;

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

        MAIN_LOGGER.info("Simulation initialization");

        Carriage carriage = new Carriage();
        createAndConnectSimulationSensorsActuators(carriage);
        SimulationOrchestrator simulationOrchestrator = new SimulationOrchestrator(carriage);
        SimulatedSmartObjectsOrchestrator simulatedSmartObjectsOrchestrator = new SimulatedSmartObjectsOrchestrator(carriage);

        MAIN_LOGGER.info("Simulation initialized successfully");
        MAIN_LOGGER.info("Starting Simulation");

        simulationOrchestrator.start();
        simulatedSmartObjectsOrchestrator.start();

        MAIN_LOGGER.info("Simulation started successfully");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            MAIN_LOGGER.info("Shutting down Simulation");
            simulatedSmartObjectsOrchestrator.stop();
            simulationOrchestrator.stop();
            MAIN_LOGGER.info("Simulations shut down successfully");
        }));
    }

    private static void createAndConnectSimulationSensorsActuators(Carriage carriage) {
        //DOORS:
        for (Door door: carriage.getExternalDoors()) {
            door.connectDoorOpenSensor(new SimulationSensorDriver<>());
            door.connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectPresenceMonitorSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
            door.getDoorLock().connectDoorLockActuator(new SimulationActuatorDriver<>());
            door.getDoorLock().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.getDoorLock().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }

        for (Door door: carriage.getInternalDoors()) {
            door.connectDoorOpenSensor(new SimulationSensorDriver<>());
            door.connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectPresenceMonitorSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.getPresenceMonitor().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
            door.getDoorLock().connectDoorLockActuator(new SimulationActuatorDriver<>());
            door.getDoorLock().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            door.getDoorLock().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }

        //CARRIAGE LIGHTS:
        for (Light light: carriage.getCarriageLights()) {
            light.connectTurnLightsOnOffActuator(new SimulationActuatorDriver<>());
            light.connectBatteryChargeSensor(new SimulationSensorDriver<>());
            light.connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }

        //TOILETS LIGHTS:
        for (Toilet toilet: carriage.getToilets()) {
            toilet.getToiletLights().connectTurnLightsOnOffActuator(new SimulationActuatorDriver<>());
            toilet.getToiletLights().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            toilet.getToiletLights().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }

        //SEATS:
        for (Seat seat: carriage.getSeats()) {
            seat.getLamp().connectTurnOnOffActuator(new SimulationActuatorDriver<>());
            seat.getLamp().connectBrightnessActuator(new SimulationActuatorDriver<>());
            seat.getLamp().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            seat.getLamp().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());

            seat.getPowerOutlet().connectCutoffPowerOutletActuator(new SimulationActuatorDriver<>());
            seat.getPowerOutlet().connectBatteryChargeSensor(new SimulationSensorDriver<>());
            seat.getPowerOutlet().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }

        //AIR VENTILATION:
        carriage.getAirVentilation().connectAirVentsActuator(new SimulationActuatorDriver<>());
        carriage.getAirVentilation().connectAirVentilationActuator(new SimulationActuatorDriver<>());
        carriage.getAirVentilation().connectDehumidifierActuator(new SimulationActuatorDriver<>());
        carriage.getAirVentilation().connectTargetAirTemperatureActuator(new SimulationActuatorDriver<>());
        carriage.getAirVentilation().connectTargetHumidityActuator(new SimulationActuatorDriver<>());
        carriage.getAirVentilation().connectAirTemperatureSensor(new SimulationSensorDriver<>());
        carriage.getAirVentilation().connectHumiditySensor(new SimulationSensorDriver<>());
        carriage.getAirVentilation().connectBatteryChargeSensor(new SimulationSensorDriver<>());
        carriage.getAirVentilation().connectEnergyConsumptionSensor(new SimulationSensorDriver<>());

        //TRASH BINS:
        for (TrashBin bin: carriage.getTrashBins()) {
            bin.connectTrashBinLockActuator(new SimulationActuatorDriver<>());
            bin.connectInternalTrashTemperatureSensor(new SimulationSensorDriver<>());
            bin.connectTrashFillPercentageSensor(new SimulationSensorDriver<>());
            bin.connectBatteryChargeSensor(new SimulationSensorDriver<>());
            bin.connectEnergyConsumptionSensor(new SimulationSensorDriver<>());
        }
    }
}