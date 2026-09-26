package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.resources.PresenceMonitoringSensorResource;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import org.javatuples.Pair;

public class PresenceMonitoringSmartObject extends SmartObjectModule {
    public PresenceMonitoringSmartObject() {
        this(null, null, null, "", 1);
    }

    public PresenceMonitoringSmartObject(SingleItemReadWriteBuffer<Pair<Integer, Integer>> inOut,
                                         SingleItemReadWriteBuffer<Double> batteryCharge,
                                         SingleItemReadWriteBuffer<Double> energyConsumption,
                                         String subfix,
                                         int deviceIndex) {
        super();

        String deviceId = String.format("presence-monitor-%s-%04d", subfix, deviceIndex);

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
