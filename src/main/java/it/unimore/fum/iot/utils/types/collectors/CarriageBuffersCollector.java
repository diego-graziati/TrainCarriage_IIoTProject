package it.unimore.fum.iot.utils.types.collectors;

import it.unimore.fum.iot.utils.tools.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class CarriageBuffersCollector {

    private final List<SingleItemReadWriteBuffer<Pair<Integer, Integer>>> doorsInOutBuffers;
    private final List<SingleItemReadWriteBuffer<Boolean>> doorsOpenCloseBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsLocksChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsPresenceMonitorsChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> carriageLightsChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> seatLampChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> seatPowerOutletChargesBuffers;
    private final SingleItemReadWriteBuffer<Double> airVentilationChargeBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> trashBinsFillPercentageBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> trashBinsInternalTemperatureBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> trashBinsChargesBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsChargeConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsLocksConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> doorsPresenceMonitorsConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> seatPowerOutletConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> trashBinsConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> carriageLightsConsumptionBuffers;
    private final List<SingleItemReadWriteBuffer<Double>> seatLampConsumptionBuffers;
    private final SingleItemReadWriteBuffer<Double> airVentilationConsumptionBuffers;


    private final List<SingleItemReadWriteBuffer<Boolean>> doorsLocksBuffers;
    private final List<SingleItemReadWriteBuffer<Boolean>> carriageLightsOnOffBuffers;
    private final List<SingleItemReadWriteBuffer<Boolean>> seatLampOnOffBuffers;
    private final List<SingleItemReadWriteBuffer<BrightnessLevelsEnum>> seatLampBrightnessBuffers;
    private final List<SingleItemReadWriteBuffer<Boolean>> seatPowerOutletOnOffBuffers;
    private final SingleItemReadWriteBuffer<Boolean> airVentsOnOffBuffer;
    private final SingleItemReadWriteBuffer<Boolean> airVentilationOnOffBuffer;
    private final SingleItemReadWriteBuffer<Boolean> dehumidifierOnOffBuffer;
    private final List<SingleItemReadWriteBuffer<Boolean>> trashBinsOnOffBuffers;

    public CarriageBuffersCollector(Carriage carriage) {
        int numDoors = carriage.getExternalDoors().size() + carriage.getInternalDoors().size();
        //REASONING: THERE IS A SINGLE LIGHT FOR EVERY SINGLE TOILET. THEY TURN ON WHEN SOMEONE IS INSIDE THE TOILET
        //AND THEY TURN OFF WHEN NONE IS INSIDE. SO I CAN JUST SUM THE NUMBER OF TOILETS IN THE CARRIAGE TO THE
        //NUMBER OF LIGHTS IN THE CARRIAGE (OUTSIDE OF TOILETS, OF COURSE)
        int numLights = carriage.getCarriageLights().size() + carriage.getToilets().size();
        int numSeats = carriage.getSeats().size();
        int numTrashBins = carriage.getTrashBins().size();

        this.doorsInOutBuffers = new ArrayList<>(numDoors);
        this.doorsOpenCloseBuffers = new ArrayList<>(numDoors);

        this.doorsChargesBuffers = new ArrayList<>(numDoors);
        this.doorsLocksChargesBuffers = new ArrayList<>(numDoors);
        this.doorsPresenceMonitorsChargesBuffers = new ArrayList<>(numDoors);
        this.carriageLightsChargesBuffers = new ArrayList<>(numLights);
        this.seatLampChargesBuffers = new ArrayList<>(numSeats);
        this.seatPowerOutletChargesBuffers = new ArrayList<>(numSeats);
        this.doorsLocksBuffers = new ArrayList<>(numDoors);
        this.carriageLightsOnOffBuffers = new ArrayList<>(numLights);
        this.seatLampOnOffBuffers = new ArrayList<>(numSeats);
        this.seatLampBrightnessBuffers = new ArrayList<>(numSeats);
        this.seatPowerOutletOnOffBuffers = new ArrayList<>(numSeats);
        this.trashBinsFillPercentageBuffers = new ArrayList<>(numTrashBins);
        this.trashBinsInternalTemperatureBuffers = new ArrayList<>(numTrashBins);
        this.trashBinsChargesBuffers = new ArrayList<>(numTrashBins);
        this.trashBinsOnOffBuffers = new ArrayList<>(numTrashBins);
        this.doorsChargeConsumptionBuffers = new ArrayList<>(numDoors);
        this.doorsLocksConsumptionBuffers = new ArrayList<>(numDoors);
        this.doorsPresenceMonitorsConsumptionBuffers = new ArrayList<>(numDoors);
        this.seatPowerOutletConsumptionBuffers = new ArrayList<>(numSeats);
        this.seatLampConsumptionBuffers = new ArrayList<>(numSeats);
        this.trashBinsConsumptionBuffers = new ArrayList<>(numTrashBins);
        this.carriageLightsConsumptionBuffers = new ArrayList<>(numDoors);

        for (int i = 0; i < numDoors; i++) {
            this.doorsInOutBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsOpenCloseBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsLocksBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsLocksChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsPresenceMonitorsChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsChargeConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsLocksConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
            this.doorsPresenceMonitorsConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
        }

        for (int i = 0; i < numLights; i++) {
            this.carriageLightsChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.carriageLightsOnOffBuffers.add(new SingleItemReadWriteBuffer<>());
            this.carriageLightsConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
        }

        for (int i = 0; i < numSeats; i++) {
            this.seatLampChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatLampOnOffBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatLampBrightnessBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatPowerOutletChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatPowerOutletOnOffBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatLampConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
            this.seatPowerOutletConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
        }

        for (int i = 0; i < numTrashBins; i++) {
            this.trashBinsFillPercentageBuffers.add(new SingleItemReadWriteBuffer<>());
            this.trashBinsInternalTemperatureBuffers.add(new SingleItemReadWriteBuffer<>());
            this.trashBinsChargesBuffers.add(new SingleItemReadWriteBuffer<>());
            this.trashBinsOnOffBuffers.add(new SingleItemReadWriteBuffer<>());
            this.trashBinsConsumptionBuffers.add(new SingleItemReadWriteBuffer<>());
        }

        this.airVentilationChargeBuffers = new SingleItemReadWriteBuffer<>();
        this.airVentsOnOffBuffer = new SingleItemReadWriteBuffer<>();
        this.airVentilationOnOffBuffer = new SingleItemReadWriteBuffer<>();
        this.dehumidifierOnOffBuffer = new SingleItemReadWriteBuffer<>();
        this.airVentilationConsumptionBuffers = new SingleItemReadWriteBuffer<>();
    }

    public List<SingleItemReadWriteBuffer<Pair<Integer, Integer>>> getDoorsInOutBuffers() {
        return doorsInOutBuffers;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getDoorsOpenCloseBuffers() {
        return doorsOpenCloseBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsChargesBuffers() {
        return doorsChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsLocksChargesBuffers() {
        return doorsLocksChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsPresenceMonitorsChargesBuffers() {
        return doorsPresenceMonitorsChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getCarriageLightsChargesBuffers() {
        return carriageLightsChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getSeatLampChargesBuffers() {
        return seatLampChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getSeatPowerOutletChargesBuffers() {
        return seatPowerOutletChargesBuffers;
    }

    public SingleItemReadWriteBuffer<Double> getAirVentilationChargeBuffers() {
        return airVentilationChargeBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getTrashBinsFillPercentageBuffers() {
        return trashBinsFillPercentageBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getTrashBinsInternalTemperatureBuffers() {
        return trashBinsInternalTemperatureBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getTrashBinsChargesBuffers() {
        return trashBinsChargesBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsChargeConsumptionBuffers() {
        return doorsChargeConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsLocksConsumptionBuffers() {
        return doorsLocksConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getDoorsPresenceMonitorsConsumptionBuffers() {
        return doorsPresenceMonitorsConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getSeatPowerOutletConsumptionBuffers() {
        return seatPowerOutletConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getTrashBinsConsumptionBuffers() {
        return trashBinsConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getCarriageLightsConsumptionBuffers() {
        return carriageLightsConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Double>> getSeatLampConsumptionBuffers() {
        return seatLampConsumptionBuffers;
    }

    public SingleItemReadWriteBuffer<Double> getAirVentilationConsumptionBuffers() {
        return airVentilationConsumptionBuffers;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getDoorsLocksBuffers() {
        return doorsLocksBuffers;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getCarriageLightsOnOffBuffers() {
        return carriageLightsOnOffBuffers;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getSeatLampOnOffBuffers() {
        return seatLampOnOffBuffers;
    }

    public List<SingleItemReadWriteBuffer<BrightnessLevelsEnum>> getSeatLampBrightnessBuffers() {
        return seatLampBrightnessBuffers;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getSeatPowerOutletOnOffBuffers() {
        return seatPowerOutletOnOffBuffers;
    }

    public SingleItemReadWriteBuffer<Boolean> getAirVentsOnOffBuffer() {
        return airVentsOnOffBuffer;
    }

    public SingleItemReadWriteBuffer<Boolean> getAirVentilationOnOffBuffer() {
        return airVentilationOnOffBuffer;
    }

    public SingleItemReadWriteBuffer<Boolean> getDehumidifierOnOffBuffer() {
        return dehumidifierOnOffBuffer;
    }

    public List<SingleItemReadWriteBuffer<Boolean>> getTrashBinsOnOffBuffers() {
        return trashBinsOnOffBuffers;
    }
}
