package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.buffers.BufferWriter;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;

public class DehumidifierActuatorModel {
    private long timestamp;
    private double targetHumidity;

    private final BufferWriter<Boolean> onOffDehumidifierWriter;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public DehumidifierActuatorModel(SingleItemReadWriteBuffer<Boolean> onOffDehumidifier) {
        this.timestamp = System.currentTimeMillis();
        this.targetHumidity = 20.0;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getTargetHumidity() {
        return targetHumidity;
    }

    public void setTargetHumidity(double targetHumidity) {
        this.targetHumidity = targetHumidity;
    }

    @Override
    public String toString() {
        return "DehumidifierActuatorModel{" +
                "timestamp=" + timestamp +
                ", humidity=" + targetHumidity +
                '}';
    }
}
