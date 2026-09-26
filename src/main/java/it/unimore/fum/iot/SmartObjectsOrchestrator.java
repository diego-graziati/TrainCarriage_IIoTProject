package it.unimore.fum.iot;

import it.unimore.fum.iot.modules.smart_objects.*;
import it.unimore.fum.iot.utils.types.collectors.CarriageBuffersCollector;
import it.unimore.fum.iot.utils.types.collectors.TemperatureHumidityBuffersCollector;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;

import java.util.ArrayList;
import java.util.List;

public class SmartObjectsOrchestrator {

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

    public SmartObjectsOrchestrator(Carriage carriage,
                                    CarriageBuffersCollector carriageBuffers,
                                    TemperatureHumidityBuffersCollector temperatureHumidityBuffers) {
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

        int i;
        for (i=0; i<carriage.getExternalDoors().size(); i++) {
            this.externalDoors.add(new DoorSensorSmartObject(carriageBuffers.getDoorsOpenCloseBuffers().get(i),
                    carriageBuffers.getDoorsChargesBuffers().get(i),
                    carriageBuffers.getDoorsChargeConsumptionBuffers().get(i),
                    "external",
                    i));
            this.externalDoorLocks.add(new DoorLockSmartObject(
                    carriageBuffers.getDoorsLocksBuffers().get(i),
                    carriageBuffers.getDoorsLocksChargesBuffers().get(i),
                    carriageBuffers.getDoorsLocksConsumptionBuffers().get(i),
                    "external",
                    i
            ));
            this.externalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    carriageBuffers.getDoorsInOutBuffers().get(i),
                    carriageBuffers.getDoorsPresenceMonitorsChargesBuffers().get(i),
                    carriageBuffers.getDoorsPresenceMonitorsConsumptionBuffers().get(i),
                    "external",
                    i
            ));
        }
        for (; i<carriage.getExternalDoors().size(); i++) {
            this.internalDoors.add(new DoorSensorSmartObject(carriageBuffers.getDoorsOpenCloseBuffers().get(i),
                    carriageBuffers.getDoorsChargesBuffers().get(i),
                    carriageBuffers.getDoorsChargeConsumptionBuffers().get(i),
                    "internal",
                    i));
            this.internalDoorLocks.add(new DoorLockSmartObject(
                    carriageBuffers.getDoorsLocksBuffers().get(i),
                    carriageBuffers.getDoorsLocksChargesBuffers().get(i),
                    carriageBuffers.getDoorsLocksConsumptionBuffers().get(i),
                    "internal",
                    i
            ));
            this.internalPresenceMonitoring.add(new PresenceMonitoringSmartObject(
                    carriageBuffers.getDoorsInOutBuffers().get(i),
                    carriageBuffers.getDoorsPresenceMonitorsChargesBuffers().get(i),
                    carriageBuffers.getDoorsPresenceMonitorsConsumptionBuffers().get(i),
                    "internal",
                    i
            ));
        }

        for (i=0; i<carriage.getSeats().size(); i++) {
            this.seatChargingStationSmartObjects.add(new SeatChargingStationSmartObject(
                    carriageBuffers.getSeatPowerOutletOnOffBuffers().get(i),
                    carriageBuffers.getSeatPowerOutletChargesBuffers().get(i),
                    carriageBuffers.getSeatPowerOutletConsumptionBuffers().get(i),
                    "",
                    i
            ));
            this.seatLightControllerSmartObjects.add(new SeatLightControllerSmartObject(
                    carriageBuffers.getSeatLampOnOffBuffers().get(i),
                    carriageBuffers.getSeatLampBrightnessBuffers().get(i),
                    carriageBuffers.getSeatLampChargesBuffers().get(i),
                    carriageBuffers.getSeatLampConsumptionBuffers().get(i),
                    "",
                    i
            ));
        }

        for (i=0; i<carriage.getTrashBins().size(); i++) {
            this.trashBinSmartObjects.add(new TrashBinSmartObject(
                    carriageBuffers.getTrashBinsOnOffBuffers().get(i),
                    carriageBuffers.getTrashBinsFillPercentageBuffers().get(i),
                    carriageBuffers.getTrashBinsInternalTemperatureBuffers().get(i),
                    carriageBuffers.getTrashBinsChargesBuffers().get(i),
                    carriageBuffers.getTrashBinsConsumptionBuffers().get(i),
                    "",
                    i
            ));
        }

        for (i=0; i<carriage.getCarriageLights().size(); i++) {
            this.carriageLightControllerSmartObjects.add(new LightControllerSmartObject(
                    carriageBuffers.getCarriageLightsOnOffBuffers().get(i),
                    carriageBuffers.getCarriageLightsChargesBuffers().get(i),
                    carriageBuffers.getCarriageLightsConsumptionBuffers().get(i),
                    "",
                    i
            ));
        }

        for (; i<carriage.getToilets().size(); i++) {
            this.toiletLightControllersSmartObjects.add(new LightControllerSmartObject(
                    carriageBuffers.getCarriageLightsOnOffBuffers().get(i),
                    carriageBuffers.getCarriageLightsChargesBuffers().get(i),
                    carriageBuffers.getCarriageLightsConsumptionBuffers().get(i),
                    "toilet",
                    i
            ));
        }

        this.temperatureControllerSmartObject = new TemperatureControllerSmartObject(
                    carriageBuffers.getDehumidifierOnOffBuffer(),
                    carriageBuffers.getAirVentsOnOffBuffer(),
                    temperatureHumidityBuffers.getTargetTemperature(),
                    temperatureHumidityBuffers.getTargetHumidity(),
                    carriageBuffers.getAirVentilationOnOffBuffer(),
                    temperatureHumidityBuffers.getHumidityBuffer(),
                    temperatureHumidityBuffers.getTemperatureBuffer(),
                    carriageBuffers.getAirVentilationChargeBuffers(),
                    carriageBuffers.getAirVentilationConsumptionBuffers(),
                    "",
                    i
        );

        Main.MAIN_LOGGER.info("All smartObjects have been successfully initialized");
    }


}
