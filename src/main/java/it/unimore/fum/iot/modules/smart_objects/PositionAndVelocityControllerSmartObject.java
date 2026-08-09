package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;

public class PositionAndVelocityControllerSmartObject extends SmartObjectModule {
    public PositionAndVelocityControllerSmartObject() {
        super();

        String deviceId = "position-velocity-controller-0001";

        this.add(new GPSSensorResource("gps",  deviceId));
        this.add(new VelocitySensorResource("velocity", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        PositionAndVelocityControllerSmartObject smartObject = new PositionAndVelocityControllerSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
