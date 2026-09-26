package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.utils.tools.buffers.BufferReader;
import it.unimore.fum.iot.utils.tools.buffers.BufferWriter;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.collectors.CarriageBuffersCollector;
import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;
import it.unimore.fum.iot.utils.types.simulation.defaults.CarriageSimulationDefaults;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarriageSimulationModule extends BaseSimulationModule {

    private final CarriageSimulationDefaults defaults;
    private final int updateFrequency;
    private final ScheduledExecutorService carriagePhaseExecutor;
    private final ScheduledExecutorService doorsSimExecutor;
    private final ScheduledExecutorService carriageLightsSimExecutor;
    private final ScheduledExecutorService seatsSimExecutor;
    private final ScheduledExecutorService airVentilationExecutor;
    private final ScheduledExecutorService trashBinsExecutor;
    private final Carriage carriage;
    private long simulationStepCounter;
    private final Random random;
    private final CarriageBuffersCollector buffers;

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
    private final BufferReader<Boolean> airVentsOnOffBufferReader;
    private final BufferReader<Boolean> dehumidifierOnOffBufferReader;
    private final List<BufferReader<Boolean>> trashBinsOnOffBufferReaders;

    private final long EMBARK_PHASE_FIRST_SIM_STEP;
    private final long BEGIN_TRAVEL_PHASE_FIST_SIM_STEP;
    private final long TRAVEL_PHASE_FIRST_SIM_STEP;
    private final long END_TRAVEL_PHASE_FIST_SIM_STEP;
    private final long DISEMBARK_PHASE_FIRST_SIM_STEP;
    private final long CARRIAGE_COMPLETE_STOP_SIM_STEP;
    private final long EMERGENCY_STARTING_STEP;

    public CarriageSimulationModule(Carriage carriage, CarriageBuffersCollector buffers, int updateFrequency) {
        super(new CarriageSimulationDefaults(Paths.Config.Simulation.CARRIAGE), updateFrequency);

        this.defaults = new CarriageSimulationDefaults(Paths.Config.Simulation.CARRIAGE);
        this.updateFrequency = updateFrequency;
        this.carriage = carriage;
        this.simulationStepCounter = 0;
        this.carriagePhaseExecutor = Executors.newScheduledThreadPool(1);
        this.doorsSimExecutor = Executors.newScheduledThreadPool(1);
        this.carriageLightsSimExecutor =  Executors.newScheduledThreadPool(1);
        this.seatsSimExecutor = Executors.newScheduledThreadPool(1);
        this.airVentilationExecutor = Executors.newScheduledThreadPool(1);
        this.trashBinsExecutor = Executors.newScheduledThreadPool(1);
        this.random = new Random();
        this.buffers = buffers;

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
            this.doorsInOutBufferWriters.add(new BufferWriter<>(buffers.getDoorsInOutBuffers().get(i)));
            this.doorsOpenCloseBufferWriters.add(new BufferWriter<>(buffers.getDoorsOpenCloseBuffers().get(i)));
            this.doorsLocksBufferReaders.add(new BufferReader<>(buffers.getDoorsLocksBuffers().get(i)));
            this.doorsChargesBufferWriters.add(new BufferWriter<>(buffers.getDoorsChargesBuffers().get(i)));
            this.doorsLocksChargesBufferWriters.add(new BufferWriter<>(buffers.getDoorsLocksChargesBuffers().get(i)));
            this.doorsPresenceMonitorsChargesBufferWriters.add(new BufferWriter<>(buffers.getDoorsPresenceMonitorsChargesBuffers().get(i)));
            this.doorsChargeConsumptionBufferWriters.add(new BufferWriter<>(buffers.getDoorsChargeConsumptionBuffers().get(i)));
            this.doorsLocksConsumptionBufferWriters.add(new BufferWriter<>(buffers.getDoorsLocksConsumptionBuffers().get(i)));
            this.doorsPresenceMonitorsConsumptionBufferWriters.add(new BufferWriter<>(buffers.getDoorsPresenceMonitorsConsumptionBuffers().get(i)));
        }

        for (int i = 0; i < numLights; i++) {
            this.carriageLightsChargesBufferWriters.add(new BufferWriter<>(buffers.getCarriageLightsChargesBuffers().get(i)));
            this.carriageLightsOnOffBufferReaders.add(new BufferReader<>(buffers.getCarriageLightsOnOffBuffers().get(i)));
            this.carriageLightsConsumptionBufferWriters.add(new BufferWriter<>(buffers.getCarriageLightsConsumptionBuffers().get(i)));
        }

        for (int i = 0; i < numSeats; i++) {
            this.seatLampChargesBufferWriters.add(new BufferWriter<>(buffers.getSeatLampChargesBuffers().get(i)));
            this.seatLampOnOffBufferReaders.add(new BufferReader<>(buffers.getSeatLampOnOffBuffers().get(i)));
            this.seatLampBrightnessBufferReaders.add(new BufferReader<>(buffers.getSeatLampBrightnessBuffers().get(i)));
            this.seatPowerOutletChargesBufferWriters.add(new BufferWriter<>(buffers.getSeatPowerOutletChargesBuffers().get(i)));
            this.seatPowerOutletOnOffBufferReaders.add(new BufferReader<>(buffers.getSeatPowerOutletOnOffBuffers().get(i)));
            this.seatLampConsumptionBufferWriters.add(new BufferWriter<>(buffers.getSeatLampConsumptionBuffers().get(i)));
            this.seatPowerOutletConsumptionBufferWriters.add(new BufferWriter<>(buffers.getSeatPowerOutletConsumptionBuffers().get(i)));
        }

        for (int i = 0; i < numTrashBins; i++) {
            this.trashBinsFillPercentageBufferWriters.add(new BufferWriter<>(buffers.getTrashBinsFillPercentageBuffers().get(i)));
            this.trashBinsInternalTemperatureBufferWriters.add(new BufferWriter<>(buffers.getTrashBinsInternalTemperatureBuffers().get(i)));
            this.trashBinsChargesBufferWriters.add(new BufferWriter<>(buffers.getTrashBinsChargesBuffers().get(i)));
            this.trashBinsOnOffBufferReaders.add(new BufferReader<>(buffers.getTrashBinsOnOffBuffers().get(i)));
            this.trashBinsConsumptionBufferWriters.add(new BufferWriter<>(buffers.getTrashBinsConsumptionBuffers().get(i)));
        }

        this.airVentilationChargeBufferWriter = new BufferWriter<>(buffers.getAirVentilationChargeBuffers());
        this.airVentilationOnOffBufferReader = new BufferReader<>(buffers.getAirVentilationOnOffBuffer());
        this.airVentsOnOffBufferReader = new BufferReader<>(buffers.getAirVentsOnOffBuffer());
        this.dehumidifierOnOffBufferReader = new BufferReader<>(buffers.getDehumidifierOnOffBuffer());
        this.airVentilationConsumptionBufferWriter = new BufferWriter<>(buffers.getAirVentilationConsumptionBuffers());

        this.EMBARK_PHASE_FIRST_SIM_STEP = 0;
        this.BEGIN_TRAVEL_PHASE_FIST_SIM_STEP = this.defaults.EMBARK_PHASE_LENGTH;
        this.TRAVEL_PHASE_FIRST_SIM_STEP = this.BEGIN_TRAVEL_PHASE_FIST_SIM_STEP + this.defaults.BEGIN_TRAVEL_PHASE_LENGTH;
        this.END_TRAVEL_PHASE_FIST_SIM_STEP = this.TRAVEL_PHASE_FIRST_SIM_STEP + this.defaults.TRAVEL_PHASE_LENGTH;
        this.DISEMBARK_PHASE_FIRST_SIM_STEP = this.END_TRAVEL_PHASE_FIST_SIM_STEP + this.defaults.END_TRAVEL_PHASE_LENGTH;
        this.CARRIAGE_COMPLETE_STOP_SIM_STEP = this.DISEMBARK_PHASE_FIRST_SIM_STEP + this.defaults.DISEMBARK_PHASE_LENGTH;
        long TOTAL_NUM_SIM_STEPS = this.defaults.BEGIN_TRAVEL_PHASE_LENGTH + this.defaults.TRAVEL_PHASE_LENGTH +
                this.defaults.END_TRAVEL_PHASE_LENGTH + this.defaults.DISEMBARK_PHASE_LENGTH;
        this.EMERGENCY_STARTING_STEP = this.random.longs(this.defaults.EMERGENCY_FLOOR_STEP_BOUND,
                TOTAL_NUM_SIM_STEPS).findFirst().getAsLong();

        Main.CARRIAGE_SIM_LOGGER.info("Is emergency enabled? " + this.defaults.ENABLE_EMERGENCY + "\nPhases first steps:\n" +
                "BEGIN_TRAVEL_PHASE_FIRST_STEP: " + this.BEGIN_TRAVEL_PHASE_FIST_SIM_STEP +
                "\nTRAVEL_PHASE_FIRST_SIM_STEP: " + this.TRAVEL_PHASE_FIRST_SIM_STEP +
                "\nEND_TRAVEL_PHASE_FIRST_SIM_STEP: " + this.END_TRAVEL_PHASE_FIST_SIM_STEP +
                "\nDISEMBARK_PHASE_FIRST_SIM_STEP: " + this.DISEMBARK_PHASE_FIRST_SIM_STEP +
                "\nCARRIAGE_COMPLETE_STOP_SIM_STEP: " + this.CARRIAGE_COMPLETE_STOP_SIM_STEP +
                "\nEMERGENCY_STARTING_STEP: " + this.EMERGENCY_STARTING_STEP);
    }

    @Override
    public void start() {
        Main.CARRIAGE_SIM_LOGGER.info("Carriage simulation starting");
        this.carriagePhaseExecutor.scheduleAtFixedRate(() -> {

            if (this.defaults.ENABLE_EMERGENCY && this.simulationStepCounter == this.EMERGENCY_STARTING_STEP) {
                this.carriage.setTravelPhase(CarriageTravelPhasesEnum.EMERGENCY);
            }

            if (this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.EMERGENCY) {
                if (simulationStepCounter == this.EMBARK_PHASE_FIRST_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.EMBARK);
                } else if (simulationStepCounter== this.BEGIN_TRAVEL_PHASE_FIST_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.BEGIN_TRAVEL);
                } else if (simulationStepCounter == this.TRAVEL_PHASE_FIRST_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.TRAVEL);
                } else if (simulationStepCounter == this.END_TRAVEL_PHASE_FIST_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.END_TRAVEL);
                } else if (simulationStepCounter == this.DISEMBARK_PHASE_FIRST_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.DISEMBARK);
                } else if (simulationStepCounter == this.CARRIAGE_COMPLETE_STOP_SIM_STEP) {
                    this.carriage.setTravelPhase(CarriageTravelPhasesEnum.COMPLETE_STOP);
                }
            }

            Main.CARRIAGE_SIM_LOGGER.info("Carriage simulation step: " + this.simulationStepCounter
                    + " | Carriage travel phase: " + this.carriage.getTravelPhase().toString());

            this.simulationStepCounter++;
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.doorsSimExecutor.scheduleAtFixedRate(() -> {
            //IMPORTANT RULE: DATA CAN ONLY BE SENT TO THE SENSORS AND RECEIVED BY ACTUATORS IF THE CORRESPONDING
            //SMART OBJECTS HAVE ENOUGH BATTERY TO PROCESS IT. OTHERWISE, THE DATA WILL BE DISCARDED.
            int i=0;
            for (Door door: this.carriage.getExternalDoors()) {
                this.resolveDoorSim(door, i);
                i ++;
            }
            for (Door door: this.carriage.getInternalDoors()) {
                this.resolveDoorSim(door, i);
                i++;
            }

        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.carriageLightsSimExecutor.scheduleAtFixedRate(() -> {
            int i=0;
            for (Light light: this.carriage.getCarriageLights()) {
                resolveLightSim(light, i);
                i++;
            }

            for (Toilet toilet: this.carriage.getToilets()) {
                resolveLightSim(toilet.getToiletLights(), i);
                i++;
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.seatsSimExecutor.scheduleAtFixedRate(() -> {
            int i=0;

            for (Seat seat: this.carriage.getSeats()) {
                resolveSeatSim(seat, i);
                i++;
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.airVentilationExecutor.scheduleAtFixedRate(this::resolveAirVentilationSim, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.trashBinsExecutor.scheduleAtFixedRate(() -> {
            int i=0;
            for (TrashBin bin: this.carriage.getTrashBins()) {
                resolveTrashBinsSim(bin, i);
                i++;
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);
        Main.CARRIAGE_SIM_LOGGER.info("Carriage simulation started");
    }

    @Override
    public void stop() {
        this.carriagePhaseExecutor.shutdown();
        this.doorsSimExecutor.shutdown();
        this.carriageLightsSimExecutor.shutdown();
        this.seatsSimExecutor.shutdown();
        this.airVentilationExecutor.shutdown();
        this.trashBinsExecutor.shutdown();
    }

    //Utility methods
    private void resolveDoorSim(Door door, int i) {
        if (door.getPresenceMonitor().getBatteryCharge() > 0.0) {
            this.doorsInOutBufferWriters.get(i).write(new Pair<>(door.getPresenceMonitor().getIn(), door.getPresenceMonitor().getOut()));
            this.doorsOpenCloseBufferWriters.get(i).write(door.isDoorOpen());
        }

        double doorPresenceMonitorChargeConsumption;
        if (door.getPresenceMonitor().isCutoff()) {
            doorPresenceMonitorChargeConsumption = door.getPresenceMonitor().getDischargeRate() - door.getPresenceMonitor().getNaturalDischargeRate();
            door.getPresenceMonitor().setBatteryCharge(door.getPresenceMonitor().getBatteryCharge() - doorPresenceMonitorChargeConsumption);
        } else {
            doorPresenceMonitorChargeConsumption = door.getPresenceMonitor().getDischargeRate() - door.getPresenceMonitor().getNaturalDischargeRate();
            door.getPresenceMonitor().setBatteryCharge(door.getPresenceMonitor().getBatteryCharge() + door.getPresenceMonitor().getBatteryCharge()
                    - doorPresenceMonitorChargeConsumption);
        }

        if (door.getPresenceMonitor().getBatteryCharge() > 0.0) {
            this.doorsPresenceMonitorsChargesBufferWriters.get(i).write(door.getPresenceMonitor().getBatteryCharge());
            this.doorsPresenceMonitorsConsumptionBufferWriters.get(i).write(doorPresenceMonitorChargeConsumption);
        }

        //FOR SAFETY REASONS, WHEN A DOOR OR ITS LOCK ARE OUT OF BATTERY CHARGE THEY CLOSE THEMSELVES. THEY CAN
        //BE OPENED MANUALLY WITH THE SAFETY SYSTEMS. THIS SHOULD ONLY HAPPEN IN EMERGENCY CASES OR ELECTRICAL
        //FAILURES!
        if (door.getDoorLock().getBatteryCharge() <= 0.0 || door.getBatteryCharge() <= 0.0) {
            door.getDoorLock().lockDoor();
            door.closeDoor();
        } else {
            if (this.doorsLocksBufferReaders.get(i).read()) {
                door.getDoorLock().lockDoor();
            } else {
                door.getDoorLock().unlockDoor();
            }
        }

        double doorLockChargeConsumption;
        double doorChargeConsumption;
        if (door.getDoorLock().isCutoff()) {
            doorLockChargeConsumption = door.getDoorLock().getDischargeRate() - door.getDoorLock().getNaturalDischargeRate();
            door.getDoorLock().setBatteryCharge(door.getDoorLock().getBatteryCharge() - doorLockChargeConsumption);
        }else {
            doorLockChargeConsumption = door.getDoorLock().getDischargeRate() - door.getDoorLock().getNaturalDischargeRate();
            door.getDoorLock().setBatteryCharge(door.getDoorLock().getBatteryCharge() + door.getDoorLock().getChargeRate()
                    - doorLockChargeConsumption);
        }
        if (door.isCutoff()) {
            doorChargeConsumption = door.getDischargeRate() - door.getNaturalDischargeRate();
            door.setBatteryCharge(door.getBatteryCharge() - doorChargeConsumption);
        } else {
            doorChargeConsumption = door.getDischargeRate() - door.getNaturalDischargeRate();
            door.setBatteryCharge(door.getBatteryCharge() + door.getChargeRate() - doorChargeConsumption);
        }

        if (door.getDoorLock().getBatteryCharge() > 0.0) {
            this.doorsLocksChargesBufferWriters.get(i).write(door.getDoorLock().getBatteryCharge());
            this.doorsLocksConsumptionBufferWriters.get(i).write(doorLockChargeConsumption);
        }
        if (door.getBatteryCharge() > 0.0) {
            this.doorsChargesBufferWriters.get(i).write(door.getBatteryCharge());
            this.doorsChargeConsumptionBufferWriters.get(i).write(doorChargeConsumption);
        }

        //NO MATTER WHAT, THE DOORS AUTOMATICALLY CLOSE THEMSELVES.
        door.closeDoor();
    }

    private void resolveLightSim(Light light, int i) {
        if (light.getBatteryCharge() > 0.0) {
            if (this.carriageLightsOnOffBufferReaders.get(i).read()) {
                light.turnLightsOn();
            } else {
                light.turnLightsOff();
            }
        }

        double lightConsumption;
        if (light.isLightsOn()) {
            if (light.isCutoff()) {
                lightConsumption = light.getDischargeRate() - light.getNaturalDischargeRate();
                light.setBatteryCharge(light.getBatteryCharge()
                        - lightConsumption);
            } else {
                lightConsumption = light.getNaturalDischargeRate() - light.getNaturalDischargeRate();
                light.setBatteryCharge(light.getBatteryCharge() + light.getChargeRate()
                        - lightConsumption);
            }
        } else {
            if (light.isCutoff()) {
                lightConsumption = light.getNaturalDischargeRate();
                light.setBatteryCharge(light.getBatteryCharge()
                        - lightConsumption);
            } else {
                lightConsumption = light.getNaturalDischargeRate();
                light.setBatteryCharge(light.getBatteryCharge() + light.getChargeRate()
                        - lightConsumption);
            }
        }

        if (light.getBatteryCharge() > 0.0) {
            this.carriageLightsChargesBufferWriters.get(i).write(light.getBatteryCharge());
            this.carriageLightsConsumptionBufferWriters.get(i).write(lightConsumption);
        }
    }

    private void resolveSeatSim(Seat seat, int i) {
        if (seat.getLamp().getBatteryCharge() > 0.0) {
            seat.getLamp().setBrightness(this.seatLampBrightnessBufferReaders.get(i).read());

            if (this.seatLampOnOffBufferReaders.get(i).read()) {
                seat.getLamp().cutoff();
            } else {
                seat.getLamp().reconnect();
            }
        } else {
            seat.getLamp().turnOff();
        }

        if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            if (this.seatPowerOutletOnOffBufferReaders.get(i).read()) {
                seat.getPowerOutlet().cutoff();
            } else {
                seat.getPowerOutlet().reconnect();
            }
        } else {
            seat.getLamp().turnOff();
        }

        double seatLampConsumption;
        if (seat.getLamp().isOn()) {
            if (seat.getLamp().isCutoff()) {
                seatLampConsumption = seat.getLamp().getDischargeRate() - seat.getLamp().getNaturalDischargeRate();
                seat.getLamp().setBatteryCharge(seat.getLamp().getBatteryCharge()
                    - seatLampConsumption);
            } else {
                seatLampConsumption = seat.getLamp().getDischargeRate() - seat.getLamp().getNaturalDischargeRate();
                seat.getLamp().setBatteryCharge(seat.getLamp().getBatteryCharge() + seat.getLamp().getChargeRate()
                        - seatLampConsumption);
            }
        } else {
            if (seat.getLamp().isCutoff()) {
                seatLampConsumption = seat.getLamp().getNaturalDischargeRate();
                seat.getLamp().setBatteryCharge(seat.getLamp().getBatteryCharge()
                        - seatLampConsumption);
            } else {
                seatLampConsumption = seat.getLamp().getNaturalDischargeRate();
                seat.getLamp().setBatteryCharge(seat.getLamp().getBatteryCharge() + seat.getLamp().getChargeRate()
                        - seatLampConsumption);
            }
        }

        double powerOutletConsumption;
        if (seat.getPowerOutlet().isOccupied()) {
            if (seat.getPowerOutlet().isCutoff()) {
                powerOutletConsumption = seat.getPowerOutlet().getDischargeRate() - seat.getPowerOutlet().getNaturalDischargeRate();
                seat.getPowerOutlet().setBatteryCharge(seat.getPowerOutlet().getBatteryCharge()
                        - powerOutletConsumption);
            } else {
                powerOutletConsumption = seat.getPowerOutlet().getDischargeRate() - seat.getPowerOutlet().getNaturalDischargeRate();
                seat.getPowerOutlet().setBatteryCharge(seat.getPowerOutlet().getBatteryCharge() + seat.getPowerOutlet().getChargeRate()
                        - powerOutletConsumption);
            }
        } else {
            if (seat.getPowerOutlet().isCutoff()) {
                powerOutletConsumption = seat.getPowerOutlet().getNaturalDischargeRate();
                seat.getPowerOutlet().setBatteryCharge(seat.getPowerOutlet().getBatteryCharge()
                        - powerOutletConsumption);
            } else {
                powerOutletConsumption = seat.getPowerOutlet().getNaturalDischargeRate();
                seat.getPowerOutlet().setBatteryCharge(seat.getPowerOutlet().getBatteryCharge() + seat.getPowerOutlet().getChargeRate()
                        - powerOutletConsumption);
            }
        }

        if (seat.getLamp().getBatteryCharge() > 0.0) {
            this.seatLampChargesBufferWriters.get(i).write(seat.getLamp().getBatteryCharge());
            this.seatLampConsumptionBufferWriters.get(i).write(seatLampConsumption);
        }

        if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            this.seatPowerOutletChargesBufferWriters.get(i).write(seat.getPowerOutlet().getBatteryCharge());
            this.seatPowerOutletConsumptionBufferWriters.get(i).write(powerOutletConsumption);
        }
    }

    private void resolveAirVentilationSim() {
        if (this.carriage.getAirVentilation().getBatteryCharge() > 0.0) {
            if(this.airVentsOnOffBufferReader.read()) {
                this.carriage.getAirVentilation().openAirVents();
            } else {
                this.carriage.getAirVentilation().closeAirVents();
            }

            if (this.airVentilationOnOffBufferReader.read()) {
                this.carriage.getAirVentilation().turnAirVentilationOn();
            } else {
                this.carriage.getAirVentilation().turnAirVentilationOff();
            }

            if (this.dehumidifierOnOffBufferReader.read()) {
                this.carriage.getAirVentilation().turnDehumidifierOn();
            } else {
                this.carriage.getAirVentilation().turnDehumidifierOff();
            }
        }

        double airVentilationConsumption;
        if (this.carriage.getAirVentilation().isAirVentilationOn() || this.carriage.getAirVentilation().isDehumidifierOn()) {
            if (this.carriage.getAirVentilation().isCutoff()) {
                airVentilationConsumption = this.carriage.getAirVentilation().getDischargeRate() -
                        this.carriage.getAirVentilation().getNaturalDischargeRate();
                this.carriage.getAirVentilation().setBatteryCharge(this.carriage.getAirVentilation().getBatteryCharge()
                        - airVentilationConsumption);
            } else {
                airVentilationConsumption = this.carriage.getAirVentilation().getDischargeRate()
                        - this.carriage.getAirVentilation().getNaturalDischargeRate();
                this.carriage.getAirVentilation().setBatteryCharge(this.carriage.getAirVentilation().getBatteryCharge()
                        + this.carriage.getAirVentilation().getChargeRate() - airVentilationConsumption);
            }
        } else {
            if (this.carriage.getAirVentilation().isCutoff()) {
                airVentilationConsumption = this.carriage.getAirVentilation().getNaturalDischargeRate();
                this.carriage.getAirVentilation().setBatteryCharge(this.carriage.getAirVentilation().getBatteryCharge()
                        - airVentilationConsumption);
            } else {
                airVentilationConsumption = this.carriage.getAirVentilation().getNaturalDischargeRate();
                this.carriage.getAirVentilation().setBatteryCharge(this.carriage.getAirVentilation().getBatteryCharge()
                        + this.carriage.getAirVentilation().getChargeRate() - airVentilationConsumption);
            }
        }

        if (this.carriage.getAirVentilation().getBatteryCharge() > 0.0) {
            this.airVentilationChargeBufferWriter.write(this.carriage.getAirVentilation().getBatteryCharge());
            this.airVentilationConsumptionBufferWriter.write(airVentilationConsumption);
        }
    }

    private void resolveTrashBinsSim(TrashBin bin, int i) {
        if (bin.getBatteryCharge() > 0.0) {
            if (this.trashBinsOnOffBufferReaders.get(i).read()) {
                bin.lockOpening();
            } else {
                bin.unlockOpening();
            }

            this.trashBinsFillPercentageBufferWriters.get(i).write(bin.getFillPercentage());
            this.trashBinsInternalTemperatureBufferWriters.get(i).write(bin.getInternalTemperature());
        } else {
            bin.unlockOpening();
        }

        double binConsumption;
        if (bin.isCutoff()) {
            binConsumption = bin.getDischargeRate() - bin.getNaturalDischargeRate();
            bin.setBatteryCharge(bin.getChargeRate()
                - binConsumption);
        } else {
            binConsumption = bin.getDischargeRate() - bin.getNaturalDischargeRate();
            bin.setBatteryCharge(bin.getChargeRate() + bin.getChargeRate()
                - binConsumption);
        }

        if (bin.getBatteryCharge() > 0.0) {
            this.trashBinsChargesBufferWriters.get(i).write(bin.getBatteryCharge());
            this.trashBinsConsumptionBufferWriters.get(i).write(binConsumption);
        }
    }
}
