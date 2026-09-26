package it.unimore.fum.iot.utils.tools.data_exchangers.simulation_entry_point;

import it.unimore.fum.iot.utils.tools.buffers.BufferWriter;
import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.tools.data_exchangers.DataWriter;
import it.unimore.fum.iot.utils.types.data.AirVentilationActuatorData;

public class AirVentilationActuatorDataExchanger implements DataWriter<AirVentilationActuatorData> {

    private final BufferWriter<Boolean> bufferWriter;

    public AirVentilationActuatorDataExchanger(SingleItemReadWriteBuffer<Boolean> buffer) {
        this.bufferWriter = new BufferWriter<>(buffer);
    }

    @Override
    public void write(AirVentilationActuatorData data) {
        this.bufferWriter.write(data.isOn());
    }
}
