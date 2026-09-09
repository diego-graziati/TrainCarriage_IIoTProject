package it.unimore.fum.iot.utils.types.collectors;

import it.unimore.fum.iot.simulation.buffers.BufferWriter;
import it.unimore.fum.iot.simulation.buffers.SingleItemReadWriteBuffer;

public class TemperatureHumidityBuffersCollector {

    private final BufferWriter<Double> temperatureBufferWriter;
    private final BufferWriter<Double> humidityBufferWriter;

    public TemperatureHumidityBuffersCollector() {
        this.temperatureBufferWriter = new BufferWriter<>(new SingleItemReadWriteBuffer<>());
        this.humidityBufferWriter = new BufferWriter<>(new SingleItemReadWriteBuffer<>());
    }

    public BufferWriter<Double> getTemperatureBufferWriter() {
        return temperatureBufferWriter;
    }

    public BufferWriter<Double> getHumidityBufferWriter() {
        return humidityBufferWriter;
    }
}
