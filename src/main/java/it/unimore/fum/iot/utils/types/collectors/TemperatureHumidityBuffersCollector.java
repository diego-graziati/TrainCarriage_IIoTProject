package it.unimore.fum.iot.utils.types.collectors;

import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class TemperatureHumidityBuffersCollector {

    private final SingleItemReadWriteBuffer<Double> temperatureBuffer;
    private final SingleItemReadWriteBuffer<Double> humidityBuffer;
    private final SingleItemReadWriteBuffer<Double> targetTemperature;
    private final SingleItemReadWriteBuffer<Double> targetHumidity;

    public TemperatureHumidityBuffersCollector() {
        this.temperatureBuffer = new SingleItemReadWriteBuffer<>();
        this.humidityBuffer = new SingleItemReadWriteBuffer<>();
        this.targetTemperature = new SingleItemReadWriteBuffer<>();
        this.targetHumidity = new SingleItemReadWriteBuffer<>();
    }

    public SingleItemReadWriteBuffer<Double> getTemperatureBuffer() {
        return temperatureBuffer;
    }

    public SingleItemReadWriteBuffer<Double> getHumidityBuffer() {
        return humidityBuffer;
    }

    public SingleItemReadWriteBuffer<Double> getTargetTemperature() {
        return targetTemperature;
    }

    public SingleItemReadWriteBuffer<Double> getTargetHumidity() {
        return targetHumidity;
    }
}
