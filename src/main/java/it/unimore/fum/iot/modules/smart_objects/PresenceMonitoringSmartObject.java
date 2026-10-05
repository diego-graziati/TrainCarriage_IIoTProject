package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.resources.PresenceMonitoringSensorResource;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.javatuples.Pair;

public class PresenceMonitoringSmartObject extends SmartObjectModule {

    public PresenceMonitoringSmartObject(SensorDriver<Pair<Integer, Integer>> presenceMonitoringSensor,
                                         SensorDriver<Double> batteryChargeSensor,
                                         SensorDriver<Double> energyConsumptionSensor,
                                         String deviceId) {
        super();

        //String deviceId = String.format("presence-monitor-%s-%04d", subfix, deviceIndex);

        this.add(new PresenceMonitoringSensorResource("presence-monitor", deviceId, presenceMonitoringSensor));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        PresenceMonitoringSmartObject smartObject = new PresenceMonitoringSmartObject(null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
