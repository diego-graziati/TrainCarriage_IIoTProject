package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class TrashBinFillSensorModel {
    private long timestamp;
    private double fillPercentage;

    private ModelStateChangeNotifier listener;

    public TrashBinFillSensorModel(SensorDriver<Double> trashBinFillPercentageSensor) {
        this.timestamp = System.currentTimeMillis();
        this.fillPercentage = 10.0;

        trashBinFillPercentageSensor.registerListeners(val -> {
            if (val < 0.0) {
                this.fillPercentage = 0.0;
            } else if (val > 100.0) {
                this.fillPercentage = 100.0;
            } else {
                this.fillPercentage = val;
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
