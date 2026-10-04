package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class TrashBinSmartObject extends SmartObjectModule {

    public TrashBinSmartObject(ActuatorDriver<Boolean> trashBinLockActuator,
                               SensorDriver<Double> trashBinFillPercentageSensor,
                               SensorDriver<Double> internalTemperatureSensor,
                               SensorDriver<Double> batteryChargeSensor,
                               SensorDriver<Double> energyConsumptionSensor,
                               String deviceId) {
        super();

        //String deviceId = String.format("trash-bin-%s-%04d", subfix, deviceIndex);

        this.add(new TrashBinLockActuatorResource("trash-bin-lock", deviceId, trashBinLockActuator));
        this.add(new TrashBinFillSensorResource("trash-bin-fill", deviceId, trashBinFillPercentageSensor));
        this.add(new TemperatureSensorResource("temperature", deviceId, internalTemperatureSensor));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        TrashBinSmartObject smartObject = new TrashBinSmartObject(null, null, null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
