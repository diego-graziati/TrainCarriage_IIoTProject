package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class TrashBinSmartObject extends SmartObjectModule {
    public TrashBinSmartObject() {
        this(null, null, null, null, null, "", 1);
    }

    public TrashBinSmartObject(SingleItemReadWriteBuffer<Boolean> trashBinLock,
                               SingleItemReadWriteBuffer<Double> trashBinFillPercentage,
                               SingleItemReadWriteBuffer<Double> internalTemperature,
                               SingleItemReadWriteBuffer<Double> batteryCharge,
                               SingleItemReadWriteBuffer<Double> energyConsumption,
                               String subfix,
                               int deviceIndex) {
        super();

        String deviceId = String.format("trash-bin-%s-%04d", subfix, deviceIndex);

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
