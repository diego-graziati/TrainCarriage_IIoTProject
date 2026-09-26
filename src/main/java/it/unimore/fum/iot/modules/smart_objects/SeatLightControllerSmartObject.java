package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;

public class SeatLightControllerSmartObject extends SmartObjectModule {
    public SeatLightControllerSmartObject() {
        this(null, null, null, null, "", 1);
    }

    public SeatLightControllerSmartObject(SingleItemReadWriteBuffer<Boolean> onOffSeatLight,
                                          SingleItemReadWriteBuffer<BrightnessLevelsEnum> brightness,
                                          SingleItemReadWriteBuffer<Double> batteryCharge,
                                          SingleItemReadWriteBuffer<Double> energyConsumption,
                                          String subfix,
                                          int deviceIndex) {
        super();

        String deviceId = String.format("seat-light-controller-%s-%04d", subfix, deviceIndex);

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
