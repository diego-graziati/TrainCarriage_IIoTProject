package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.DoorSensorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;

public class DoorSensorSmartObject extends SmartObjectModule {
    public DoorSensorSmartObject() {
        super();

        String deviceId = "door-sensor-0001";

        this.add(new DoorSensorResource("door", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        DoorSensorSmartObject smartObject = new DoorSensorSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
