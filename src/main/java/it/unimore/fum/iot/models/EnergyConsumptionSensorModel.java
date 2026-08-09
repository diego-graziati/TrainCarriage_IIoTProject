package it.unimore.fum.iot.models;

public class EnergyConsumptionSensorModel {
    private long timestamp;
    private long energyConsumption;

    private String energyConsumptionUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public EnergyConsumptionSensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.energyConsumption = 10;
        this.energyConsumptionUnit = "W";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getEnergyConsumption() {
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
