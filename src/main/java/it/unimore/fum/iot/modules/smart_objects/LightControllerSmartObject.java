package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class LightControllerSmartObject extends SmartObjectModule {

    public LightControllerSmartObject(ActuatorDriver<Boolean> lightOnOffActuator,
                                      SensorDriver<Double> batteryChargeSensor,
                                      SensorDriver<Double> energyConsumptionSensor,
                                      String deviceId) {
        super();

        //String deviceId = String.format("light-controller-%s-%04d", subfix, deviceIndex);

        this.add(new SwitchOnOffActuatorResource("switch-on-off", deviceId, lightOnOffActuator));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        LightControllerSmartObject smartObject = new LightControllerSmartObject(null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
