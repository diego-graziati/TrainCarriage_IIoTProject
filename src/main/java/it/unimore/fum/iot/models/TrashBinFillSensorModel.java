package it.unimore.fum.iot.models;

public class TrashBinFillSensorModel {
    private long timestamp;
    private double fillPercentage;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public TrashBinFillSensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.fillPercentage = 10.0;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    @Override
    public String toString() {
        return "TrashBinFillSensorModel{" +
                "timestamp=" + timestamp +
                ", fillPercentage=" + fillPercentage +
                '}';
    }
}
