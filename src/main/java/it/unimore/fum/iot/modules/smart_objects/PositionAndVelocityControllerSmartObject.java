package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.coap.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.coap.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.coap.resources.GPSSensorResource;
import it.unimore.fum.iot.coap.resources.VelocitySensorResource;
import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.javatuples.Pair;

public class PositionAndVelocityControllerSmartObject extends SmartObjectModule {
    public PositionAndVelocityControllerSmartObject(SensorDriver<Pair<Double, Double>> gpsSensor,
                                                    SensorDriver<Double> velocitySensor,
                                                    SensorDriver<Double> batteryChargeSensor,
                                                    SensorDriver<Double> energyConsumptionSensor,
                                                    CoapEndpoint endpoint,
                                                    String deviceId,
                                                    String rdUriInfoConfigPath,
                                                    String loggerGroupName) {
        super(deviceId, rdUriInfoConfigPath, loggerGroupName);

        if (endpoint != null) {
            this.addEndpoint(endpoint);
        }

        this.add(new GPSSensorResource("gps",  deviceId, gpsSensor));
        this.add(new VelocitySensorResource("velocity", deviceId, velocitySensor));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId, batteryChargeSensor));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId, energyConsumptionSensor));
    }

    public static void main(String[] args) {
        PositionAndVelocityControllerSmartObject smartObject = new PositionAndVelocityControllerSmartObject(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
