package it.unimore.fum.iot.models;

public class VelocitySensorModel {
    private long timestamp;
    private double velocity;
    private String velocityUnit;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public VelocitySensorModel() {
        this.timestamp = System.currentTimeMillis();
        this.velocity = 0;
        this.velocityUnit = "m/s";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public String getVelocityUnit() {
        return velocityUnit;
    }

    public void setVelocityUnit(String velocityUnit) {
        this.velocityUnit = velocityUnit;
    }

    @Override
    public String toString() {
        return "VelocitySensorModel{" +
                "timestamp=" + timestamp +
                ", velocity=" + velocity + " " + velocityUnit + '\'' +
                '}';
    }
}
