package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.simulation.buffers.BufferReader;
import it.unimore.fum.iot.simulation.buffers.BufferWriter;
import it.unimore.fum.iot.simulation.buffers.SingleItemReadWriteBuffer;
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

        this.airVentilationExecutor.scheduleAtFixedRate(() -> {
            if (this.carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                if (this.buffers.getAirVentilationOnOffBufferReader().read()) {
                    this.carriage.getAirVentilation().openAirVents();
                } else {
                    this.carriage.getAirVentilation().closeAirVents();
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
                this.buffers.getAirVentilationChargeBufferWriter().write(this.carriage.getAirVentilation().getBatteryCharge());
                this.buffers.getAirVentilationConsumptionBufferWriter().write(airVentilationConsumption);
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

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
    }

    //Utility methods
    private void resolveDoorSim(Door door, int i) {
        if (door.getPresenceMonitor().getBatteryCharge() > 0.0) {
            this.buffers.getDoorsInOutBufferWriters().get(i).write(new Pair<>(door.getPresenceMonitor().getIn(), door.getPresenceMonitor().getOut()));
            this.buffers.getDoorsOpenCloseBufferWriters().get(i).write(door.isDoorOpen());
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
            this.buffers.getDoorsPresenceMonitorsChargesBufferWriters().get(i).write(door.getPresenceMonitor().getBatteryCharge());
            this.buffers.getDoorsPresenceMonitorsConsumptionBufferWriters().get(i).write(doorPresenceMonitorChargeConsumption);
        }

        //FOR SAFETY REASONS, WHEN A DOOR OR ITS LOCK ARE OUT OF BATTERY CHARGE THEY CLOSE THEMSELVES. THEY CAN
        //BE OPENED MANUALLY WITH THE SAFETY SYSTEMS. THIS SHOULD ONLY HAPPEN IN EMERGENCY CASES OR ELECTRICAL
        //FAILURES!
        if (door.getDoorLock().getBatteryCharge() <= 0.0 || door.getBatteryCharge() <= 0.0) {
            door.getDoorLock().lockDoor();
            door.closeDoor();
        } else {
            if (this.buffers.getDoorsLocksBufferReaders().get(i).read()) {
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
            this.buffers.getDoorsLocksChargesBufferWriters().get(i).write(door.getDoorLock().getBatteryCharge());
            this.buffers.getDoorsLocksConsumptionBufferWriters().get(i).write(doorLockChargeConsumption);
        }
        if (door.getBatteryCharge() > 0.0) {
            this.buffers.getDoorsChargesBufferWriters().get(i).write(door.getBatteryCharge());
            this.buffers.getDoorsChargeConsumptionBufferWriters().get(i).write(doorChargeConsumption);
        }
    }

    private void resolveLightSim(Light light, int i) {
        if (light.getBatteryCharge() > 0.0) {
            if (this.buffers.getCarriageLightsOnOffBufferReaders().get(i).read()) {
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
            this.buffers.getCarriageLightsChargesBufferWriters().get(i).write(light.getBatteryCharge());
            this.buffers.getCarriageLightsConsumptionBufferWriters().get(i).write(lightConsumption);
        }
    }

    private void resolveSeatSim(Seat seat, int i) {
        if (seat.getLamp().getBatteryCharge() > 0.0) {
            seat.getLamp().setBrightness(this.buffers.getSeatLampBrightnessBufferReaders().get(i).read());

            if (this.buffers.getSeatLampOnOffBufferReaders().get(i).read()) {
                seat.getLamp().cutoff();
            } else {
                seat.getLamp().reconnect();
            }
        } else {
            seat.getLamp().turnOff();
        }

        if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            if (this.buffers.getSeatPowerOutletOnOffBufferReaders().get(i).read()) {
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
            this.buffers.getSeatLampChargesBufferWriters().get(i).write(seat.getLamp().getBatteryCharge());
            this.buffers.getSeatLampConsumptionBufferWriters().get(i).write(seatLampConsumption);
        }

        if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            this.buffers.getSeatPowerOutletChargesBufferWriters().get(i).write(seat.getPowerOutlet().getBatteryCharge());
            this.buffers.getSeatPowerOutletConsumptionBufferWriters().get(i).write(powerOutletConsumption);
        }
    }

    private void resolveTrashBinsSim(TrashBin bin, int i) {
        if (bin.getBatteryCharge() > 0.0) {
            if (this.buffers.getTrashBinsOnOffBufferReaders().get(i).read()) {
                bin.lockOpening();
            } else {
                bin.unlockOpening();
            }

            this.buffers.getTrashBinsFillPercentageBufferWriters().get(i).write(bin.getFillPercentage());
            this.buffers.getTrashBinsInternalTemperatureBufferWriters().get(i).write(bin.getInternalTemperature());
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
            this.buffers.getTrashBinsChargesBufferWriters().get(i).write(bin.getBatteryCharge());
            this.buffers.getTrashBinsConsumptionBufferWriters().get(i).write(binConsumption);
        }
    }
}
