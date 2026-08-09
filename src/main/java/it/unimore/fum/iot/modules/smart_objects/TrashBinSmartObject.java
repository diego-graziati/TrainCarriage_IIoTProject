package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;

public class TrashBinSmartObject extends SmartObjectModule {
    public TrashBinSmartObject() {
        super();

        String deviceId = "trash-bin-0001";

        this.add(new TrashBinLockActuatorResource("trash-bin-lock", deviceId));
        this.add(new TrashBinFillSensorResource("trash-bin-fill", deviceId));
        this.add(new TemperatureSensorResource("temperature", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        TrashBinSmartObject smartObject = new TrashBinSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
