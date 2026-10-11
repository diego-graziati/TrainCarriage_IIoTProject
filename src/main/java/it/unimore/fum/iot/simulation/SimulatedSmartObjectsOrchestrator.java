package it.unimore.fum.iot.simulation;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.coap.ResourceDirectory;
import it.unimore.fum.iot.modules.smart_objects.*;
import it.unimore.fum.iot.utils.Paths;
import it.unimore.fum.iot.utils.Settings;
import it.unimore.fum.iot.utils.tools.CustomLogFormatter;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;
import org.eclipse.californium.core.network.CoapEndpoint;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimulatedSmartObjectsOrchestrator {

    private final List<DoorSensorSmartObject> externalDoors;
    private final List<DoorSensorSmartObject> internalDoors;
    private final List<DoorLockSmartObject> externalDoorLocks;
    private final List<DoorLockSmartObject> internalDoorLocks;
    private final List<PresenceMonitoringSmartObject> externalPresenceMonitoring;
    private final List<PresenceMonitoringSmartObject> internalPresenceMonitoring;
    private final List<SeatChargingStationSmartObject> seatChargingStationSmartObjects;
    private final List<SeatLightControllerSmartObject> seatLightControllerSmartObjects;
    private final TemperatureControllerSmartObject temperatureControllerSmartObject;
    private final List<TrashBinSmartObject> trashBinSmartObjects;
    private final List<LightControllerSmartObject> carriageLightControllerSmartObjects;
    private final List<LightControllerSmartObject> toiletLightControllersSmartObjects;

    //The RD should be the first thing online and the last thing to go offline, so that it can serve all smart objects.
    private final ResourceDirectory rd;

    private final List<Logger> loggerGroups;
    private final List<Handler> loggerGroupsHandlers;

    public SimulatedSmartObjectsOrchestrator(Carriage carriage) {
        Main.MAIN_LOGGER.info("SmartObjects initialization");

        //LOGGERS CREATION
        String loggerExternalDoorsName = Settings.SmartObjects.Logs.EXTERNAL_DOORS_LOGGER_GROUP_NAME;
        String loggerInternalDoorsName = Settings.SmartObjects.Logs.INTERNAL_DOORS_LOGGER_GROUP_NAME;
        String loggerExternalDoorLocksName = Settings.SmartObjects.Logs.EXTERNAL_DOOR_LOCKS_LOGGER_GROUP_NAME;
        String loggerInternalDoorLocksName = Settings.SmartObjects.Logs.INTERNAL_DOOR_LOCKS_LOGGER_GROUP_NAME;
        String loggerExternalPresenceMonitoringName = Settings.SmartObjects.Logs.EXTERNAL_PRESENCE_MONITORING_GROUP_NAME;
        String loggerInternalPresenceMonitoringName = Settings.SmartObjects.Logs.INTERNAL_PRESENCE_MONITORING_GROUP_NAME;
        String loggerSeatChargingStationsName = Settings.SmartObjects.Logs.SEAT_CHARGING_STATIONS_GROUP_NAME;
        String loggerSeatLightControllersName = Settings.SmartObjects.Logs.SEAT_LIGHT_CONTROLLERS_GROUP_NAME;
        String loggerTrashBinsName = Settings.SmartObjects.Logs.TRASH_BINS_GROUP_NAME;
        String loggerCarriageLightControllersName = Settings.SmartObjects.Logs.CARRIAGE_LIGHT_CONTROLLERS_GROUP_NAME;
        String loggerToiletLightControllersName = Settings.SmartObjects.Logs.TOILET_LIGHT_CONTROLLERS_GROUP_NAME;
        String loggerTemperatureControllerName = Settings.SmartObjects.Logs.TEMPERATURE_CONTROLLER_GROUP_NAME;

        this.loggerGroups = new ArrayList<>();
        this.loggerGroupsHandlers = new ArrayList<>();

        this.createLoggersGroups(
                loggerExternalDoorsName,
                loggerInternalDoorsName,
                loggerExternalDoorLocksName,
                loggerInternalDoorLocksName,
                loggerExternalPresenceMonitoringName,
                loggerInternalPresenceMonitoringName,
                loggerSeatChargingStationsName,
                loggerSeatLightControllersName,
                loggerTrashBinsName,
                loggerCarriageLightControllersName,
                loggerToiletLightControllersName,
                loggerTemperatureControllerName
        );

        //CREATE the resource directory
        this.rd = new ResourceDirectory(5683);

        //PORT=0 => The OS decides which port to use in each case, if any available.
        //PORT!=0 => If not given, the default is used. If given and free it's used, otherwise not.
        int port = 0;

        this.externalDoors = new ArrayList<>(carriage.getExternalDoors().size());
        this.internalDoors = new ArrayList<>(carriage.getInternalDoors().size());
        this.externalDoorLocks = new ArrayList<>(carriage.getExternalDoors().size());
        this.internalDoorLocks = new ArrayList<>(carriage.getInternalDoors().size());
        this.externalPresenceMonitoring = new ArrayList<>(carriage.getExternalDoors().size());
        this.internalPresenceMonitoring = new ArrayList<>(carriage.getInternalDoors().size());
        this.seatChargingStationSmartObjects = new ArrayList<>(carriage.getSeats().size());
        this.seatLightControllerSmartObjects = new ArrayList<>(carriage.getSeats().size());
        this.trashBinSmartObjects = new ArrayList<>(carriage.getTrashBins().size());
        this.carriageLightControllerSmartObjects = new ArrayList<>(carriage.getCarriageLights().size());
        this.toiletLightControllersSmartObjects = new ArrayList<>(carriage.getToilets().size());

        int i = 0;
        for (Door door: carriage.getExternalDoors()) {
            this.externalDoors.add(new DoorSensorSmartObject(door.getDoorOpenSensor(),
                    door.getBatteryChargeSensor(),
                    door.getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "door-sensor-external-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerExternalDoorsName
            ));
            this.externalDoorLocks.add(new DoorLockSmartObject(
                    door.getDoorLock().getDoorLockActuator(),
                    door.getDoorLock().getBatteryChargeSensor(),
                    door.getDoorLock().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "door-lock-external-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerExternalDoorLocksName
            ));
            this.externalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    door.getPresenceMonitor().getPresenceMonitorSensor(),
                    door.getPresenceMonitor().getBatteryChargeSensor(),
                    door.getPresenceMonitor().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "presence-monitor-external-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerExternalPresenceMonitoringName
            ));
            i++;
        }

        i = 0;
        for (Door door: carriage.getInternalDoors()) {
            this.internalDoors.add(new DoorSensorSmartObject(door.getDoorOpenSensor(),
                    door.getBatteryChargeSensor(),
                    door.getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "door-sensor-internal-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerInternalDoorsName
            ));
            this.internalDoorLocks.add(new DoorLockSmartObject(
                    door.getDoorLock().getDoorLockActuator(),
                    door.getDoorLock().getBatteryChargeSensor(),
                    door.getDoorLock().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "door-lock-internal-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerInternalDoorLocksName
            ));
            this.internalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    door.getPresenceMonitor().getPresenceMonitorSensor(),
                    door.getPresenceMonitor().getBatteryChargeSensor(),
                    door.getPresenceMonitor().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "presence-monitor-internal-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerInternalPresenceMonitoringName
            ));
            i++;
        }

        i = 0;
        for (Seat seat: carriage.getSeats()) {
            this.seatChargingStationSmartObjects.add(new SeatChargingStationSmartObject(
                    seat.getPowerOutlet().getCutoffPowerOutletActuator(),
                    seat.getPowerOutlet().getBatteryChargeSensor(),
                    seat.getPowerOutlet().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "seat-charging-station-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerSeatChargingStationsName
            ));
            this.seatLightControllerSmartObjects.add(new SeatLightControllerSmartObject(
                    seat.getLamp().getTurnOnOffActuator(),
                    seat.getLamp().getBrightnessActuator(),
                    seat.getLamp().getBatteryChargeSensor(),
                    seat.getLamp().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "seat-light-controller-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerSeatLightControllersName
            ));
            i++;
        }

        i = 0;
        Main.MAIN_LOGGER.info("Number of trash bins: " + carriage.getTrashBins().size());
        for (TrashBin bin: carriage.getTrashBins()) {
            this.trashBinSmartObjects.add(new TrashBinSmartObject(
                    bin.getTrashBinLockActuator(),
                    bin.getTrashFillPercentageSensor(),
                    bin.getInternalTrashTemperatureSensor(),
                    bin.getBatteryChargeSensor(),
                    bin.getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "trash-bin-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerTrashBinsName
            ));
            i++;
        }

        i = 0;
        Main.MAIN_LOGGER.info("Number carriage lights: " + carriage.getCarriageLights().size());
        for (Light light: carriage.getCarriageLights()) {
            this.carriageLightControllerSmartObjects.add(new LightControllerSmartObject(
                    light.getTurnLightsOnOffActuator(),
                    light.getBatteryChargeSensor(),
                    light.getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "light-controller-carriage-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerCarriageLightControllersName
            ));
            i++;
        }

        i = 0;
        Main.MAIN_LOGGER.info("Number of toilets: " + carriage.getToilets().size());
        for (Toilet toilet: carriage.getToilets()) {
            this.toiletLightControllersSmartObjects.add(new LightControllerSmartObject(
                    toilet.getToiletLights().getTurnLightsOnOffActuator(),
                    toilet.getToiletLights().getBatteryChargeSensor(),
                    toilet.getToiletLights().getEnergyConsumptionSensor(),
                    this.createEndpoint(port),
                    "light-controller-toilet-" + i,
                    Paths.Config.CoAP.RESOURCE_DIRECTORY,
                    loggerToiletLightControllersName
            ));
            i++;
        }

        Main.MAIN_LOGGER.info("Is there an air ventilation? " + (carriage.getAirVentilation() != null));
        this.temperatureControllerSmartObject = new TemperatureControllerSmartObject(
            carriage.getAirVentilation().getDehumidifierActuator(),
            carriage.getAirVentilation().getAirVentsActuator(),
            carriage.getAirVentilation().getTargetAirTemperatureActuator(),
            carriage.getAirVentilation().getTargetHumidityActuator(),
            carriage.getAirVentilation().getAirVentilationActuator(),
            carriage.getAirVentilation().getHumiditySensor(),
            carriage.getAirVentilation().getAirTemperatureSensor(),
            carriage.getAirVentilation().getBatteryChargerSensor(),
            carriage.getAirVentilation().getEnergyConsumptionSensor(),
            this.createEndpoint(port),
            "temperature-controller",
            Paths.Config.CoAP.RESOURCE_DIRECTORY,
            loggerTemperatureControllerName
        );

        Main.MAIN_LOGGER.info("All smartObjects have been successfully initialized");
    }

    public void start() {
        rd.start();

        for (DoorSensorSmartObject smartObject: this.externalDoors) {
            smartObject.start();
        }
        for (DoorSensorSmartObject smartObject: this.internalDoors) {
            smartObject.start();
        }
        for (DoorLockSmartObject smartObject: this.externalDoorLocks) {
            smartObject.start();
        }
        for (DoorLockSmartObject smartObject: this.internalDoorLocks) {
            smartObject.start();
        }
        for (PresenceMonitoringSmartObject smartObject: this.externalPresenceMonitoring) {
            smartObject.start();
        }
        for (PresenceMonitoringSmartObject smartObject: this.internalPresenceMonitoring) {
            smartObject.start();
        }
        for (SeatChargingStationSmartObject smartObject: this.seatChargingStationSmartObjects) {
            smartObject.start();
        }
        for (SeatLightControllerSmartObject smartObject: this.seatLightControllerSmartObjects) {
            smartObject.start();
        }
        for (TrashBinSmartObject smartObject: this.trashBinSmartObjects) {
            smartObject.start();
        }
        for (LightControllerSmartObject smartObject: this.carriageLightControllerSmartObjects) {
            smartObject.start();
        }
        for (LightControllerSmartObject smartObject: this.toiletLightControllersSmartObjects) {
            smartObject.start();
        }
    }

    public void stop() {
        for (DoorSensorSmartObject smartObject: this.externalDoors) {
            smartObject.stop();
        }
        for (DoorSensorSmartObject smartObject: this.internalDoors) {
            smartObject.stop();
        }
        for (DoorLockSmartObject smartObject: this.externalDoorLocks) {
            smartObject.stop();
        }
        for (DoorLockSmartObject smartObject: this.internalDoorLocks) {
            smartObject.stop();
        }
        for (PresenceMonitoringSmartObject smartObject: this.externalPresenceMonitoring) {
            smartObject.stop();
        }
        for (PresenceMonitoringSmartObject smartObject: this.internalPresenceMonitoring) {
            smartObject.stop();
        }
        for (SeatChargingStationSmartObject smartObject: this.seatChargingStationSmartObjects) {
            smartObject.stop();
        }
        for (SeatLightControllerSmartObject smartObject: this.seatLightControllerSmartObjects) {
            smartObject.stop();
        }
        for (TrashBinSmartObject smartObject: this.trashBinSmartObjects) {
            smartObject.stop();
        }
        for (LightControllerSmartObject smartObject: this.carriageLightControllerSmartObjects) {
            smartObject.stop();
        }
        for (LightControllerSmartObject smartObject: this.toiletLightControllersSmartObjects) {
            smartObject.stop();
        }

        rd.stop();
    }

    public List<Handler> getLoggerGroupsHandlers() {
        return this.loggerGroupsHandlers;
    }

    private CoapEndpoint createEndpoint(int port) {
        CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
        builder.setInetSocketAddress(new InetSocketAddress(port));
        return builder.build();
    }

    private void createLoggersGroups(
            String loggerExternalDoorsName,
            String loggerInternalDoorsName,
            String loggerExternalDoorLocksName,
            String loggerInternalDoorLocksName,
            String loggerExternalPresenceMonitoringName,
            String loggerInternalPresenceMonitoringName,
            String loggerSeatChargingStationsName,
            String loggerSeatLightControllersName,
            String loggerTrashBinsName,
            String loggerCarriageLightControllersName,
            String loggerToiletLightControllersName,
            String loggerTemperatureControllerName
    ) {
        try {
            Logger loggerExternalDoors = Logger.getLogger(loggerExternalDoorsName);
            loggerExternalDoors.setUseParentHandlers(false);
            this.loggerGroups.add(loggerExternalDoors);

            File logFile = new File("logs/smart_objects/external-doors.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerExternalDoors.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerInternalDoors = Logger.getLogger(loggerInternalDoorsName);
            loggerInternalDoors.setUseParentHandlers(false);
            this.loggerGroups.add(loggerInternalDoors);

            File logFile = new File("logs/smart_objects/internal-doors.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerInternalDoors.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerExternalDoorLocks = Logger.getLogger(loggerExternalDoorLocksName);
            loggerExternalDoorLocks.setUseParentHandlers(false);
            this.loggerGroups.add(loggerExternalDoorLocks);

            File logFile = new File("logs/smart_objects/external-door-locks.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerExternalDoorLocks.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerInternalDoorLocks = Logger.getLogger(loggerInternalDoorLocksName);
            loggerInternalDoorLocks.setUseParentHandlers(false);
            this.loggerGroups.add(loggerInternalDoorLocks);

            File logFile = new File("logs/smart_objects/internal-door-locks.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerInternalDoorLocks.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerExternalPresenceMonitoring = Logger.getLogger(loggerExternalPresenceMonitoringName);
            loggerExternalPresenceMonitoring.setUseParentHandlers(false);
            this.loggerGroups.add(loggerExternalPresenceMonitoring);

            File logFile = new File("logs/smart_objects/external-presence-monitoring.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerExternalPresenceMonitoring.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerInternalPresenceMonitoring = Logger.getLogger(loggerInternalPresenceMonitoringName);
            loggerInternalPresenceMonitoring.setUseParentHandlers(false);
            this.loggerGroups.add(loggerInternalPresenceMonitoring);

            File logFile = new File("logs/smart_objects/internal-presence-monitoring.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerInternalPresenceMonitoring.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerSeatChargingStations = Logger.getLogger(loggerSeatChargingStationsName);
            loggerSeatChargingStations.setUseParentHandlers(false);
            this.loggerGroups.add(loggerSeatChargingStations);

            File logFile = new File("logs/smart_objects/seat-charging-stations.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerSeatChargingStations.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerSeatLightControllers = Logger.getLogger(loggerSeatLightControllersName);
            loggerSeatLightControllers.setUseParentHandlers(false);
            this.loggerGroups.add(loggerSeatLightControllers);

            File logFile = new File("logs/smart_objects/seat-light-controllers.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerSeatLightControllers.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerTrashBins = Logger.getLogger(loggerTrashBinsName);
            loggerTrashBins.setUseParentHandlers(false);
            this.loggerGroups.add(loggerTrashBins);

            File logFile = new File("logs/smart_objects/trash-bins.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerTrashBins.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerCarriageLightControllers = Logger.getLogger(loggerCarriageLightControllersName);
            loggerCarriageLightControllers.setUseParentHandlers(false);
            this.loggerGroups.add(loggerCarriageLightControllers);

            File logFile = new File("logs/smart_objects/carriage-light-controllers.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerCarriageLightControllers.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerToiletLightControllers = Logger.getLogger(loggerToiletLightControllersName);
            loggerToiletLightControllers.setUseParentHandlers(false);
            this.loggerGroups.add(loggerToiletLightControllers);

            File logFile = new File("logs/smart_objects/toilet-light-controllers.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerToiletLightControllers.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
        try {
            Logger loggerTemperatureController = Logger.getLogger(loggerTemperatureControllerName);
            loggerTemperatureController.setUseParentHandlers(false);
            this.loggerGroups.add(loggerTemperatureController);

            File logFile = new File("logs/smart_objects/temperature-controller.log");
            FileHandler fh = createLoggerFileHandler(logFile);

            //If fh is null this logger will print nothing.
            if (fh != null) {
                fh.setFormatter(new CustomLogFormatter());
            }
            loggerTemperatureController.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Unable to create log file:\n" + e.getMessage());
        }
    }

    private FileHandler createLoggerFileHandler(File logFile) throws IOException {
        //If parentFile == null then the logFile isn't inside any dir.
        if (logFile.getParentFile() != null && !logFile.getParentFile().exists()) {
            if (!logFile.getParentFile().mkdirs()) {
                Main.MAIN_LOGGER.warning("Unable to create log directories: " + logFile.getParentFile().getAbsolutePath());
                return null;
            }
        }
        if (logFile.exists()) {
            if (!logFile.delete()) {
                Main.MAIN_LOGGER.warning("Unable to delete log file: " + logFile.getAbsolutePath());
                return null;
            }
        }

        try {
            FileHandler fh = new FileHandler(logFile.getPath(), false);
            this.loggerGroupsHandlers.add(fh);
            return fh;
        } catch (IOException e) {
            Main.MAIN_LOGGER.warning("Unable to create log file: " + logFile.getAbsolutePath());
            return null;
        }
    }
}
