package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class DoorSensorModel {
    private long timestamp;
    private boolean isOpen;

    private ModelStateChangeNotifier listener;

    public DoorSensorModel(SensorDriver<Boolean> isDoorOpenSensor) {
        this.timestamp = System.currentTimeMillis();
        this.isOpen = false;

        isDoorOpenSensor.registerListeners(val -> {
            this.isOpen = val;

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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "DoorSensorModel{" +
                "timestamp=" + timestamp +
                ", isOpen=" + isOpen +
                '}';
    }
}
