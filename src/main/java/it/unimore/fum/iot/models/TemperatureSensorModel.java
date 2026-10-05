package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;

public class TemperatureSensorModel {
    private long timestamp;
    private double temperature;

    private String temperatureUnit;

    private ModelStateChangeNotifier listener;

    public TemperatureSensorModel(SensorDriver<Double> temperatureSensor) {
        this.timestamp = System.currentTimeMillis();
        this.temperature = 10.0;
        this.temperatureUnit = "Cel";

        temperatureSensor.registerListeners(val -> {
            this.temperature = val;

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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    @Override
    public String toString() {
        return "TemperatureSensorModel{" +
                "timestamp=" + timestamp +
                ", temperature=" + temperature + " " + temperatureUnit +
                '}';
    }
}
