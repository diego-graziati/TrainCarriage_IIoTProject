package it.unimore.fum.iot.modules.smart_objects;

import it.unimore.fum.iot.modules.SmartObjectModule;
import it.unimore.fum.iot.resources.BatteryChargeSensorResource;
import it.unimore.fum.iot.resources.EnergyConsumptionSensorResource;
import it.unimore.fum.iot.resources.SeatChargerActuatorResource;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class SeatChargingStationSmartObject extends SmartObjectModule {
    public SeatChargingStationSmartObject() {
        this(null, null, null, "", 1);
    }

    public SeatChargingStationSmartObject(SingleItemReadWriteBuffer<Boolean> onOffPowerOutlet,
                                          SingleItemReadWriteBuffer<Double> batteryCharge,
                                          SingleItemReadWriteBuffer<Double> energyConsumption,
                                          String subfix,
                                          int deviceIndex) {
        super();

        String deviceId = String.format("seat-charging-station-%s-%04d", subfix, deviceIndex);

        this.add(new SeatChargerActuatorResource("seat-charger", deviceId));
        this.add(new BatteryChargeSensorResource("battery-charge", deviceId));
        this.add(new EnergyConsumptionSensorResource("energy-consumption", deviceId));
    }

    public static void main(String[] args) {
        SeatChargingStationSmartObject smartObject = new SeatChargingStationSmartObject();
        smartObject.start();

        smartObject.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
