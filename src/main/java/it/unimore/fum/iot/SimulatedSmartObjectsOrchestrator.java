package it.unimore.fum.iot;

import it.unimore.fum.iot.modules.smart_objects.*;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;

import java.util.ArrayList;
import java.util.List;

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

    public SimulatedSmartObjectsOrchestrator(Carriage carriage) {
        Main.MAIN_LOGGER.info("SmartObjects initialization");

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
                    "door-sensor-external-" + i
            ));
            this.externalDoorLocks.add(new DoorLockSmartObject(
                    door.getDoorLock().getDoorLockActuator(),
                    door.getDoorLock().getBatteryChargeSensor(),
                    door.getDoorLock().getEnergyConsumptionSensor(),
                    "door-lock-external-" + i
            ));
            this.externalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    door.getPresenceMonitor().getPresenceMonitorSensor(),
                    door.getPresenceMonitor().getBatteryChargeSensor(),
                    door.getPresenceMonitor().getEnergyConsumptionSensor(),
                    "presence-monitor-external-" + i
            ));
            i++;
        }

        i = 0;
        for (Door door: carriage.getInternalDoors()) {
            this.internalDoors.add(new DoorSensorSmartObject(door.getDoorOpenSensor(),
                    door.getBatteryChargeSensor(),
                    door.getEnergyConsumptionSensor(),
                    "door-sensor-internal-" + i
            ));
            this.internalDoorLocks.add(new DoorLockSmartObject(
                    door.getDoorLock().getDoorLockActuator(),
                    door.getDoorLock().getBatteryChargeSensor(),
                    door.getDoorLock().getEnergyConsumptionSensor(),
                    "door-lock-internal-" + i
            ));
            this.internalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    door.getPresenceMonitor().getPresenceMonitorSensor(),
                    door.getPresenceMonitor().getBatteryChargeSensor(),
                    door.getPresenceMonitor().getEnergyConsumptionSensor(),
                    "presence-monitor-internal-" + i
            ));
            i++;
        }

        i = 0;
        for (Seat seat: carriage.getSeats()) {
            this.seatChargingStationSmartObjects.add(new SeatChargingStationSmartObject(
                    seat.getPowerOutlet().getCutoffPowerOutletActuator(),
                    seat.getPowerOutlet().getBatteryChargeSensor(),
                    seat.getPowerOutlet().getEnergyConsumptionSensor(),
                    "seat-charging-station-" + i
            ));
            this.seatLightControllerSmartObjects.add(new SeatLightControllerSmartObject(
                    seat.getLamp().getTurnOnOffActuator(),
                    seat.getLamp().getBrightnessActuator(),
                    seat.getLamp().getBatteryChargeSensor(),
                    seat.getLamp().getEnergyConsumptionSensor(),
                    "seat-light-controller-" + i
            ));
            i++;
        }

        i = 0;
        for (TrashBin bin: carriage.getTrashBins()) {
            this.trashBinSmartObjects.add(new TrashBinSmartObject(
                    bin.getTrashBinLockActuator(),
                    bin.getTrashFillPercentageSensor(),
                    bin.getInternalTrashTemperatureSensor(),
                    bin.getBatteryChargeSensor(),
                    bin.getEnergyConsumptionSensor(),
                    "trash-bin-" + i
            ));
            i++;
        }

        i = 0;
        for (Light light: carriage.getCarriageLights()) {
            this.carriageLightControllerSmartObjects.add(new LightControllerSmartObject(
                    light.getTurnLightsOnOffActuator(),
                    light.getBatteryChargeSensor(),
                    light.getEnergyConsumptionSensor(),
                    "light-controller-carriage-" + i
            ));
            i++;
        }

        i = 0;
        for (Toilet toilet: carriage.getToilets()) {
            this.toiletLightControllersSmartObjects.add(new LightControllerSmartObject(
                    toilet.getToiletLights().getTurnLightsOnOffActuator(),
                    toilet.getToiletLights().getBatteryChargeSensor(),
                    toilet.getToiletLights().getEnergyConsumptionSensor(),
                    "light-controller-toilet-" + i
            ));
            i++;
        }

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
                    "temperature-controller"
        );

        Main.MAIN_LOGGER.info("All smartObjects have been successfully initialized");
    }

    public void start() {
        for (DoorSensorSmartObject externalDoor: this.externalDoors) {
            externalDoor.start();
        }
        for (DoorSensorSmartObject internalDoor: this.internalDoors) {
            internalDoor.start();
        }
        for (DoorLockSmartObject externalDoorLock: this.externalDoorLocks) {
            externalDoorLock.start();
        }
        for (DoorLockSmartObject internalDoorLock: this.internalDoorLocks) {
            internalDoorLock.start();
        }
        for (PresenceMonitoringSmartObject externalPresenceMonitoring: this.externalPresenceMonitoring) {
            externalPresenceMonitoring.start();
        }
        for (PresenceMonitoringSmartObject internalPresenceMonitoring: this.internalPresenceMonitoring) {
            internalPresenceMonitoring.start();
        }
        for (SeatChargingStationSmartObject seatChargingStationSmartObject: this.seatChargingStationSmartObjects) {
            seatChargingStationSmartObject.start();
        }
        for (SeatLightControllerSmartObject seatLightControllerSmartObject: this.seatLightControllerSmartObjects) {
            seatLightControllerSmartObject.start();
        }
        for (TrashBinSmartObject trashBinSmartObject: this.trashBinSmartObjects) {
            trashBinSmartObject.start();
        }
        for (LightControllerSmartObject carriageLightController: this.carriageLightControllerSmartObjects) {
            carriageLightController.start();
        }
        for (LightControllerSmartObject toiletLightController: this.toiletLightControllersSmartObjects) {
            toiletLightController.start();
        }
    }

    public void stop() {
        for (DoorSensorSmartObject externalDoor: this.externalDoors) {
            externalDoor.stop();
        }
        for (DoorSensorSmartObject internalDoor: this.internalDoors) {
            internalDoor.stop();
        }
        for (DoorLockSmartObject externalDoorLock: this.externalDoorLocks) {
            externalDoorLock.stop();
        }
        for (DoorLockSmartObject internalDoorLock: this.internalDoorLocks) {
            internalDoorLock.stop();
        }
        for (PresenceMonitoringSmartObject externalPresenceMonitoring: this.externalPresenceMonitoring) {
            externalPresenceMonitoring.stop();
        }
        for (PresenceMonitoringSmartObject internalPresenceMonitoring: this.internalPresenceMonitoring) {
            internalPresenceMonitoring.stop();
        }
        for (SeatChargingStationSmartObject seatChargingStationSmartObject: this.seatChargingStationSmartObjects) {
            seatChargingStationSmartObject.stop();
        }
        for (SeatLightControllerSmartObject seatLightControllerSmartObject: this.seatLightControllerSmartObjects) {
            seatLightControllerSmartObject.stop();
        }
        for (TrashBinSmartObject trashBinSmartObject: this.trashBinSmartObjects) {
            trashBinSmartObject.stop();
        }
        for (LightControllerSmartObject carriageLightController: this.carriageLightControllerSmartObjects) {
            carriageLightController.stop();
        }
        for (LightControllerSmartObject toiletLightController: this.toiletLightControllersSmartObjects) {
            toiletLightController.stop();
        }
    }
}
