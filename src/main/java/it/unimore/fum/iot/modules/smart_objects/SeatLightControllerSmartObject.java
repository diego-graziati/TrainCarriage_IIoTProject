package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class SeatLightControllerSmartObject extends SmartObjectModule {

    public SeatLightControllerSmartObject(ActuatorDriver<Boolean> onOffSeatLightActuator,
                                          ActuatorDriver<BrightnessLevelsEnum> brightnessActuator,
                                          SensorDriver<Double> batteryChargeSensor,
                                          SensorDriver<Double> energyConsumptionSensor,
                                          String deviceId) {
        super();

        //String deviceId = String.format("seat-light-controller-%s-%04d", subfix, deviceIndex);

        this.add(new SwitchOnOffActuatorResource("switch-on-off", deviceId, onOffSeatLightActuator));
        this.add(new LampBrightessActuatorResource("lamp-brightness", deviceId, brightnessActuator));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        SeatLightControllerSmartObject smartObject = new SeatLightControllerSmartObject(null, null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
