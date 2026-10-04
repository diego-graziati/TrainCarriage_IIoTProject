package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.DoorLockActuatorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class DoorLockSmartObject extends SmartObjectModule {

    public DoorLockSmartObject(ActuatorDriver<Boolean> doorLockActuator,
                               SensorDriver<Double> batteryChargeSensor,
                               SensorDriver<Double> energyConsumptionSensor,
                               String deviceId) {
        super();

        //String deviceId = String.format("door-lock-%s-%04d", subfix, deviceIndex);

        this.add(new DoorLockActuatorResource("door-lock", deviceId, doorLockActuator));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        DoorLockSmartObject smartObject = new DoorLockSmartObject(null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
