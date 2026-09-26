package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class TemperatureControllerSmartObject extends SmartObjectModule {
    public TemperatureControllerSmartObject() {
        this(null, null, null, null, null, null, null, null, null, "",1);
    }

    public TemperatureControllerSmartObject(SingleItemReadWriteBuffer<Boolean> onOffDehumidifier,
                                            SingleItemReadWriteBuffer<Boolean> onOffAirVents,
                                            SingleItemReadWriteBuffer<Double> setTargetTemperature,
                                            SingleItemReadWriteBuffer<Double> setTargetHumidity,
                                            SingleItemReadWriteBuffer<Boolean> onOffAirVentilation,
                                            SingleItemReadWriteBuffer<Double> humidity,
                                            SingleItemReadWriteBuffer<Double> temperature,
                                            SingleItemReadWriteBuffer<Double> batteryCharge,
                                            SingleItemReadWriteBuffer<Double> energyConsumption,
                                            String subfix,
                                            int deviceIndex) {
        super();

        String deviceId = String.format("temperature-controller-%s-%04d", subfix, deviceIndex);

        this.add(new DehumidifierActuatorResource("dehumidifier", deviceId, onOffDehumidifier));
        this.add(new AirTemperatureActuatorResource("air-temperature", deviceId));
        this.add(new AirVentilationActuatorResource("air-ventilation", deviceId));
        this.add(new HumiditySensorResource("humidity", deviceId));
        this.add(new TemperatureSensorResource("temperature", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        TemperatureControllerSmartObject smartObject = new TemperatureControllerSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
