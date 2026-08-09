package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;

public class TemperatureControllerSmartObject extends SmartObjectModule {
    public TemperatureControllerSmartObject() {
        super();

        String deviceId = "temperature-controller-0001";

        this.add(new DehumidifierActuatorResource("dehumidifier", deviceId));
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
