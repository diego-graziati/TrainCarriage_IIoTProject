package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.DoorLockActuatorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;

public class DoorLockSmartObject extends SmartObjectModule {
    public DoorLockSmartObject() {
        super();

        String deviceId = "door-lock-0001";

        this.add(new DoorLockActuatorResource("door-lock", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        DoorLockSmartObject smartObject = new DoorLockSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
