package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class LightControllerSmartObject extends SmartObjectModule {
    public LightControllerSmartObject() {
        this(null, null, null, "", 1);
    }

    public LightControllerSmartObject(SingleItemReadWriteBuffer<Boolean> lightOnOff,
                                      SingleItemReadWriteBuffer<Double> batteryCharge,
                                      SingleItemReadWriteBuffer<Double> energyConsumption,
                                      String subfix,
                                      int deviceIndex) {
        super();

        String deviceId = String.format("light-controller-%s-%04d", subfix, deviceIndex);

        this.add(new SwitchOnOffActuatorResource("switch-on-off", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        LightControllerSmartObject smartObject = new LightControllerSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
