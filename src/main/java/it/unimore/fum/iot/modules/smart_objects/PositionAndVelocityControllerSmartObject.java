package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.*;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.javatuples.Pair;
import org.javatuples.Tuple;

public class PositionAndVelocityControllerSmartObject extends SmartObjectModule {
    public PositionAndVelocityControllerSmartObject(SensorDriver<Pair<Double, Double>> gpsSensor,
                                                    SensorDriver<Double> velocitySensor,
                                                    SensorDriver<Double> batteryChargeSensor,
                                                    SensorDriver<Double> energyConsumptionSensor,
                                                    String deviceId) {
        super();

        //String deviceId = "position-velocity-controller-0001";

        this.add(new GPSSensorResource("gps",  deviceId, gpsSensor));
        this.add(new VelocitySensorResource("velocity", deviceId, velocitySensor));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        PositionAndVelocityControllerSmartObject smartObject = new PositionAndVelocityControllerSmartObject(null, null, null, null, null);
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
