package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class EnergyConsumptionSensorModel {
    private long timestamp;
    private double energyConsumption;

    private String energyConsumptionUnit;

    private ModelStateChangeNotifier listener;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public EnergyConsumptionSensorModel(SensorDriver<Double> energyConsumptionSensor) {
        this.timestamp = System.currentTimeMillis();
        this.energyConsumption = 10;
        this.energyConsumptionUnit = "W";

        energyConsumptionSensor.registerListeners(val -> {
            if (val < 0) {
                this.energyConsumption = 0;
            } else {
                this.energyConsumption = val;
            }

            if (this.listener != null) {
                this.listener.onStateChange();
            }
        });
    }

    public void setOnStateChange(ModelStateChangeNotifier listener) {
        this.listener = listener;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(long energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public String getEnergyConsumptionUnit() {
        return energyConsumptionUnit;
    }

    public void setEnergyConsumptionUnit(String energyConsumptionUnit) {
        this.energyConsumptionUnit = energyConsumptionUnit;
    }

    @Override
    public String toString() {
        return "EnergyConsumptionSensorModel{" +
                "timestamp=" + timestamp +
                ", energyConsumption=" + energyConsumption + " " + energyConsumptionUnit +
                '}';
    }
}
