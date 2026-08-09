package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;

public class SeatLightControllerSmartObject extends SmartObjectModule {
    public SeatLightControllerSmartObject() {
        super();

        String deviceId = "seat-light-controller-0001";

        this.add(new SwitchOnOffActuatorResource("switch-on-off", deviceId));
        this.add(new LampBrightessActuatorResource("lamp-brightness", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        SeatLightControllerSmartObject smartObject = new SeatLightControllerSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
