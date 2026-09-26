package it.unimore.fum.iot.utils.tools.data_exchangers.simulation_entry_point;

import it.unimore.fum.iot.utils.tools.buffers.BufferWriter;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.tools.data_exchangers.DataWriter;
import it.unimore.fum.iot.utils.types.data.AirTemperatureActuatorData;

public class AirTemperatureActuatorDataExchanger implements DataWriter<AirTemperatureActuatorData> {

    private final BufferWriter<Double> bufferWriter;

    public AirTemperatureActuatorDataExchanger(SingleItemReadWriteBuffer<Double> buffer) {
        this.bufferWriter = new BufferWriter<>(buffer);
    }

    @Override
    public void write(AirTemperatureActuatorData data) {
        this.bufferWriter.write(data.getTargetTemperature());
    }
}
