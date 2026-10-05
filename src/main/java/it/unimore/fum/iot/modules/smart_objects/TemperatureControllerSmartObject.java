package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class TemperatureControllerSmartObject extends SmartObjectModule {

    public TemperatureControllerSmartObject(ActuatorDriver<Boolean> onOffDehumidifierActuator,
                                            ActuatorDriver<Boolean> onOffAirVentsActuator,
                                            ActuatorDriver<Double> setTargetTemperatureActuator,
                                            ActuatorDriver<Double> setTargetHumidityActuator,
                                            ActuatorDriver<Boolean> onOffAirVentilationActuator,
                                            SensorDriver<Double> humiditySensor,
                                            SensorDriver<Double> temperatureSensor,
                                            SensorDriver<Double> batteryChargeSensor,
                                            SensorDriver<Double> energyConsumptionSensor,
                                            String deviceId) {
        super();

        //String deviceId = String.format("temperature-controller-%s-%04d", subfix, deviceIndex);

        this.add(new DehumidifierActuatorResource("dehumidifier", deviceId, onOffDehumidifierActuator));
        this.add(new AirTemperatureActuatorResource("air-temperature", deviceId, setTargetTemperatureActuator));
        this.add(new AirVentilationActuatorResource("air-ventilation", deviceId, onOffAirVentilationActuator));
        this.add(new HumiditySensorResource("humidity", deviceId, humiditySensor));
        this.add(new TemperatureSensorResource("temperature", deviceId, temperatureSensor));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        TemperatureControllerSmartObject smartObject = new TemperatureControllerSmartObject(null, null, null, null, null, null, null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
