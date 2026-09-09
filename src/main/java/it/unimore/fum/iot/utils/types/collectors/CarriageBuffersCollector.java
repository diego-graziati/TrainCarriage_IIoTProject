package it.unimore.fum.iot.utils.types.collectors;

import it.unimore.fum.iot.simulation.buffers.BufferReader;
import it.unimore.fum.iot.simulation.buffers.BufferWriter;
import it.unimore.fum.iot.simulation.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class CarriageBuffersCollector {

    private final List<BufferWriter<Pair<Integer, Integer>>> doorsInOutBufferWriters;
    private final List<BufferWriter<Boolean>> doorsOpenCloseBufferWriters;
    private final List<BufferWriter<Double>> doorsChargesBufferWriters;
    private final List<BufferWriter<Double>> doorsLocksChargesBufferWriters;
    private final List<BufferWriter<Double>> doorsPresenceMonitorsChargesBufferWriters;
    private final List<BufferWriter<Double>> carriageLightsChargesBufferWriters;
    private final List<BufferWriter<Double>> seatLampChargesBufferWriters;
    private final List<BufferWriter<Double>> seatPowerOutletChargesBufferWriters;
    private final BufferWriter<Double> airVentilationChargeBufferWriter;
    private final List<BufferWriter<Double>> trashBinsFillPercentageBufferWriters;
    private final List<BufferWriter<Double>> trashBinsInternalTemperatureBufferWriters;
    private final List<BufferWriter<Double>> trashBinsChargesBufferWriters;
    private final List<BufferWriter<Double>> doorsChargeConsumptionBufferWriters;
    private final List<BufferWriter<Double>> doorsLocksConsumptionBufferWriters;
    private final List<BufferWriter<Double>> doorsPresenceMonitorsConsumptionBufferWriters;
    private final List<BufferWriter<Double>> seatPowerOutletConsumptionBufferWriters;
    private final List<BufferWriter<Double>> trashBinsConsumptionBufferWriters;
    private final List<BufferWriter<Double>> carriageLightsConsumptionBufferWriters;
    private final List<BufferWriter<Double>> seatLampConsumptionBufferWriters;
    private final BufferWriter<Double> airVentilationConsumptionBufferWriter;


    private final List<BufferReader<Boolean>> doorsLocksBufferReaders;
    private final List<BufferReader<Boolean>> carriageLightsOnOffBufferReaders;
    private final List<BufferReader<Boolean>> seatLampOnOffBufferReaders;
    private final List<BufferReader<BrightnessLevelsEnum>> seatLampBrightnessBufferReaders;
    private final List<BufferReader<Boolean>> seatPowerOutletOnOffBufferReaders;
    private final BufferReader<Boolean> airVentilationOnOffBufferReader;
    private final List<BufferReader<Boolean>> trashBinsOnOffBufferReaders;

    public CarriageBuffersCollector(Carriage carriage) {
        int numDoors = carriage.getExternalDoors().size() + carriage.getInternalDoors().size();
        //REASONING: THERE IS A SINGLE LIGHT FOR EVERY SINGLE TOILET. THEY TURN ON WHEN SOMEONE IS INSIDE THE TOILET
        //AND THEY TURN OFF WHEN NONE IS INSIDE. SO I CAN JUST SUM THE NUMBER OF TOILETS IN THE CARRIAGE TO THE
        //NUMBER OF LIGHTS IN THE CARRIAGE (OUTSIDE OF TOILETS, OF COURSE)
        int numLights = carriage.getCarriageLights().size() + carriage.getToilets().size();
        int numSeats = carriage.getSeats().size();
        int numTrashBins = carriage.getTrashBins().size();

        this.doorsInOutBufferWriters = new ArrayList<>(numDoors);
        this.doorsOpenCloseBufferWriters = new ArrayList<>(numDoors);

        this.doorsChargesBufferWriters = new ArrayList<>(numDoors);
        this.doorsLocksChargesBufferWriters = new ArrayList<>(numDoors);
        this.doorsPresenceMonitorsChargesBufferWriters = new ArrayList<>(numDoors);
        this.carriageLightsChargesBufferWriters = new ArrayList<>(numLights);
        this.seatLampChargesBufferWriters = new ArrayList<>(numSeats);
        this.seatPowerOutletChargesBufferWriters = new ArrayList<>(numSeats);
        this.doorsLocksBufferReaders = new ArrayList<>(numDoors);
        this.carriageLightsOnOffBufferReaders = new ArrayList<>(numLights);
        this.seatLampOnOffBufferReaders = new ArrayList<>(numSeats);
        this.seatLampBrightnessBufferReaders = new ArrayList<>(numSeats);
        this.seatPowerOutletOnOffBufferReaders = new ArrayList<>(numSeats);
        this.trashBinsFillPercentageBufferWriters = new ArrayList<>(numTrashBins);
        this.trashBinsInternalTemperatureBufferWriters = new ArrayList<>(numTrashBins);
        this.trashBinsChargesBufferWriters = new ArrayList<>(numTrashBins);
        this.trashBinsOnOffBufferReaders = new ArrayList<>(numTrashBins);
        this.doorsChargeConsumptionBufferWriters = new ArrayList<>(numDoors);
        this.doorsLocksConsumptionBufferWriters = new ArrayList<>(numDoors);
        this.doorsPresenceMonitorsConsumptionBufferWriters = new ArrayList<>(numDoors);
        this.seatPowerOutletConsumptionBufferWriters = new ArrayList<>(numSeats);
        this.seatLampConsumptionBufferWriters = new ArrayList<>(numSeats);
        this.trashBinsConsumptionBufferWriters = new ArrayList<>(numTrashBins);
        this.carriageLightsConsumptionBufferWriters = new ArrayList<>(numDoors);

        for (int i = 0; i < numDoors; i++) {
            this.doorsInOutBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsOpenCloseBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsLocksBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.doorsChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsLocksChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsPresenceMonitorsChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsChargeConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsLocksConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.doorsPresenceMonitorsConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
        }

        for (int i = 0; i < numLights; i++) {
            this.carriageLightsChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.carriageLightsOnOffBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.carriageLightsConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
        }

        for (int i = 0; i < numSeats; i++) {
            this.seatLampChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.seatLampOnOffBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.seatLampBrightnessBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.seatPowerOutletChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.seatPowerOutletOnOffBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.seatLampConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.seatPowerOutletConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
        }

        for (int i = 0; i < numTrashBins; i++) {
            this.trashBinsFillPercentageBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.trashBinsInternalTemperatureBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.trashBinsChargesBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
            this.trashBinsOnOffBufferReaders.add(new BufferReader<>(new SingleItemReadWriteBuffer<>()));
            this.trashBinsConsumptionBufferWriters.add(new BufferWriter<>(new SingleItemReadWriteBuffer<>()));
        }

        this.airVentilationChargeBufferWriter = new BufferWriter<>(new SingleItemReadWriteBuffer<>());
        this.airVentilationOnOffBufferReader = new BufferReader<>(new SingleItemReadWriteBuffer<>());
        this.airVentilationConsumptionBufferWriter = new BufferWriter<>(new SingleItemReadWriteBuffer<>());
    }

    public List<BufferWriter<Pair<Integer, Integer>>> getDoorsInOutBufferWriters() {
        return doorsInOutBufferWriters;
    }

    public List<BufferWriter<Boolean>> getDoorsOpenCloseBufferWriters() {
        return doorsOpenCloseBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsChargesBufferWriters() {
        return doorsChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsLocksChargesBufferWriters() {
        return doorsLocksChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsPresenceMonitorsChargesBufferWriters() {
        return doorsPresenceMonitorsChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getCarriageLightsChargesBufferWriters() {
        return carriageLightsChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getSeatLampChargesBufferWriters() {
        return seatLampChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getSeatPowerOutletChargesBufferWriters() {
        return seatPowerOutletChargesBufferWriters;
    }

    public BufferWriter<Double> getAirVentilationChargeBufferWriter() {
        return airVentilationChargeBufferWriter;
    }

    public List<BufferWriter<Double>> getTrashBinsFillPercentageBufferWriters() {
        return trashBinsFillPercentageBufferWriters;
    }

    public List<BufferWriter<Double>> getTrashBinsInternalTemperatureBufferWriters() {
        return trashBinsInternalTemperatureBufferWriters;
    }

    public List<BufferWriter<Double>> getTrashBinsChargesBufferWriters() {
        return trashBinsChargesBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsChargeConsumptionBufferWriters() {
        return doorsChargeConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsLocksConsumptionBufferWriters() {
        return doorsLocksConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getDoorsPresenceMonitorsConsumptionBufferWriters() {
        return doorsPresenceMonitorsConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getSeatPowerOutletConsumptionBufferWriters() {
        return seatPowerOutletConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getTrashBinsConsumptionBufferWriters() {
        return trashBinsConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getCarriageLightsConsumptionBufferWriters() {
        return carriageLightsConsumptionBufferWriters;
    }

    public List<BufferWriter<Double>> getSeatLampConsumptionBufferWriters() {
        return seatLampConsumptionBufferWriters;
    }

    public BufferWriter<Double> getAirVentilationConsumptionBufferWriter() {
        return airVentilationConsumptionBufferWriter;
    }

    public List<BufferReader<Boolean>> getDoorsLocksBufferReaders() {
        return doorsLocksBufferReaders;
    }

    public List<BufferReader<Boolean>> getCarriageLightsOnOffBufferReaders() {
        return carriageLightsOnOffBufferReaders;
    }

    public List<BufferReader<Boolean>> getSeatLampOnOffBufferReaders() {
        return seatLampOnOffBufferReaders;
    }

    public List<BufferReader<BrightnessLevelsEnum>> getSeatLampBrightnessBufferReaders() {
        return seatLampBrightnessBufferReaders;
    }

    public List<BufferReader<Boolean>> getSeatPowerOutletOnOffBufferReaders() {
        return seatPowerOutletOnOffBufferReaders;
    }

    public BufferReader<Boolean> getAirVentilationOnOffBufferReader() {
        return airVentilationOnOffBufferReader;
    }

    public List<BufferReader<Boolean>> getTrashBinsOnOffBufferReaders() {
        return trashBinsOnOffBufferReaders;
    }
}
