package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.resources.PresenceMonitoringSensorResource;

public class PresenceMonitoringSmartObject extends SmartObjectModule {
    public PresenceMonitoringSmartObject() {
        super();

        String deviceId = "presence-monitor-0001";

        this.add(new PresenceMonitoringSensorResource("presence-monitor", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        PresenceMonitoringSmartObject smartObject = new PresenceMonitoringSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
