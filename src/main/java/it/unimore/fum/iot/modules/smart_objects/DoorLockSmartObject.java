package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.DoorLockActuatorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class DoorLockSmartObject extends SmartObjectModule {
    public DoorLockSmartObject() {
        this(null, null, null, "", 1);
    }

    public DoorLockSmartObject(SingleItemReadWriteBuffer<Boolean> lockDoor,
                               SingleItemReadWriteBuffer<Double> batteryCharge,
                               SingleItemReadWriteBuffer<Double> energyConsumption,
                               String subfix,
                               int deviceIndex) {
        super();

        String deviceId = String.format("door-lock-%s-%04d", subfix, deviceIndex);

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
