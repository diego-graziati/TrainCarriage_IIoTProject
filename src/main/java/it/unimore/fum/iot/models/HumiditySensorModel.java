package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class HumiditySensorModel {
    private long timestamp;
    private double humidity;

    private ModelStateChangeNotifier listener;

    public HumiditySensorModel(SensorDriver<Double> humiditySensor) {
        this.timestamp = System.currentTimeMillis();
        this.humidity = 40.0;

        humiditySensor.registerListeners(val -> {
            this.humidity = val;

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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "HumiditySensorModel{" +
                "timestamp=" + timestamp +
                ", humidity=" + humidity +
                '}';
    }
}
