package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;
import it.unimore.fum.iot.utils.types.simulation.defaults.CarriageSimulationDefaults;
import org.javatuples.Pair;

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

    private final long EMBARK_PHASE_FIRST_SIM_STEP;
    private final long BEGIN_TRAVEL_PHASE_FIST_SIM_STEP;
    private final long TRAVEL_PHASE_FIRST_SIM_STEP;
    private final long END_TRAVEL_PHASE_FIST_SIM_STEP;
    private final long DISEMBARK_PHASE_FIRST_SIM_STEP;
    private final long CARRIAGE_COMPLETE_STOP_SIM_STEP;
    private final long EMERGENCY_STARTING_STEP;

    public CarriageSimulationModule(Carriage carriage, int updateFrequency) {
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
            for (Door door: this.carriage.getExternalDoors()) {
                this.resolveDoorSim(door);
            }
            for (Door door: this.carriage.getInternalDoors()) {
                this.resolveDoorSim(door);
            }

        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.carriageLightsSimExecutor.scheduleAtFixedRate(() -> {
            for (Light light: this.carriage.getCarriageLights()) {
                resolveLightSim(light);
            }

            for (Toilet toilet: this.carriage.getToilets()) {
                resolveLightSim(toilet.getToiletLights());
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.seatsSimExecutor.scheduleAtFixedRate(() -> {
            for (Seat seat: this.carriage.getSeats()) {
                resolveSeatSim(seat);
            }
        }, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.airVentilationExecutor.scheduleAtFixedRate(this::resolveAirVentilationSim, 0, 60/this.updateFrequency, TimeUnit.SECONDS);

        this.trashBinsExecutor.scheduleAtFixedRate(() -> {
            for (TrashBin bin: this.carriage.getTrashBins()) {
                resolveTrashBinsSim(bin);
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
    private void resolveDoorSim(Door door) {
        if (door.getPresenceMonitor().getBatteryCharge() > 0.0 && door.getPresenceMonitor().getPresenceMonitorSensor() != null) {
            door.getPresenceMonitor().getPresenceMonitorSensor().update(
                    new Pair<>(door.getPresenceMonitor().getIn(), door.getPresenceMonitor().getOut()));

        }
        if (door.getBatteryCharge() > 0.0 && door.getDoorOpenSensor() != null) {
            door.getDoorOpenSensor().update(door.isDoorOpen());
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

        if (door.getPresenceMonitor().getBatteryCharge() > 0.0 && door.getPresenceMonitor().getPresenceMonitorSensor() != null) {
            if (door.getPresenceMonitor().getBatteryChargeSensor() != null) {
                door.getPresenceMonitor().getBatteryChargeSensor().update(door.getPresenceMonitor().getBatteryCharge());
            }
            if (door.getPresenceMonitor().getEnergyConsumptionSensor() != null) {
                door.getPresenceMonitor().getEnergyConsumptionSensor().update(doorPresenceMonitorChargeConsumption);
            }
        }

        //FOR SAFETY REASONS, WHEN A DOOR OR ITS LOCK ARE OUT OF BATTERY CHARGE THEY CLOSE THEMSELVES. THEY CAN
        //BE OPENED MANUALLY WITH THE SAFETY SYSTEMS. THIS SHOULD ONLY HAPPEN IN EMERGENCY CASES OR ELECTRICAL
        //FAILURES!
        if (door.getDoorLock().getBatteryCharge() <= 0.0 || door.getBatteryCharge() <= 0.0) {
            door.getDoorLock().lockDoor();
            door.closeDoor();
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
            if (door.getDoorLock().getBatteryChargeSensor() != null) {
                door.getDoorLock().getBatteryChargeSensor().update(door.getDoorLock().getBatteryCharge());
            }
            if (door.getDoorLock().getEnergyConsumptionSensor() != null) {
                door.getDoorLock().getEnergyConsumptionSensor().update(doorLockChargeConsumption);
            }
        }
        if (door.getBatteryCharge() > 0.0) {
            if (door.getBatteryChargeSensor() != null) {
                door.getBatteryChargeSensor().update(door.getBatteryCharge());
            }
            if (door.getEnergyConsumptionSensor() != null) {
                door.getEnergyConsumptionSensor().update(doorChargeConsumption);
            }
        }

        //NO MATTER WHAT, THE DOORS AUTOMATICALLY CLOSE THEMSELVES.
        door.closeDoor();
    }

    private void resolveLightSim(Light light) {
        /*if (light.getBatteryCharge() > 0.0) {
            if (this.carriageLightsOnOffBufferReaders.get(i).read()) {
                light.turnLightsOn();
            } else {
                light.turnLightsOff();
            }
        }*/

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
            if (light.getBatteryChargeSensor() != null) {
                light.getBatteryChargeSensor().update(light.getBatteryCharge());
            }
            if (light.getEnergyConsumptionSensor() != null) {
                light.getEnergyConsumptionSensor().update(lightConsumption);
            }
        }
    }

    private void resolveSeatSim(Seat seat) {
        /*if (seat.getLamp().getBatteryCharge() > 0.0) {
            seat.getLamp().setBrightness(this.seatLampBrightnessBufferReaders.get(i).read());

            if (this.seatLampOnOffBufferReaders.get(i).read()) {
                seat.getLamp().cutoff();
            } else {
                seat.getLamp().repair();
            }
        } else {
            seat.getLamp().turnOff();
        }*/

        /*if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            if (this.seatPowerOutletOnOffBufferReaders.get(i).read()) {
                seat.getPowerOutlet().cutoff();
            } else {
                seat.getPowerOutlet().repair();
            }
        } else {
            seat.getLamp().turnOff();
        }*/

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
            if (seat.getLamp().getBatteryChargeSensor() != null) {
                seat.getLamp().getBatteryChargeSensor().update(seat.getLamp().getBatteryCharge());
            }
            if (seat.getLamp().getEnergyConsumptionSensor() != null) {
                seat.getLamp().getEnergyConsumptionSensor().update(seatLampConsumption);
            }
        }

        if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
            if (seat.getPowerOutlet().getBatteryChargeSensor() != null) {
                seat.getPowerOutlet().getBatteryChargeSensor().update(seat.getPowerOutlet().getBatteryCharge());
            }
            if (seat.getPowerOutlet().getEnergyConsumptionSensor() != null) {
                seat.getPowerOutlet().getEnergyConsumptionSensor().update(powerOutletConsumption);
            }
        }
    }

    private void resolveAirVentilationSim() {
        /*if (this.carriage.getAirVentilation().getBatteryCharge() > 0.0) {
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
        }*/

        //FOR SAFETY REASONS, IF THE BATTERY CHARGE REACHES 0.0 THE AIR VENTS ARE OPENED, AS TO ENSURE AIR FLOW
        //IN AN EMERGENCY SCENARIO!!
        if (this.carriage.getAirVentilation().getBatteryCharge() <= 0.0) {
            this.carriage.getAirVentilation().openAirVents();
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
            if (this.carriage.getAirVentilation().getBatteryChargerSensor() != null) {
                this.carriage.getAirVentilation().getBatteryChargerSensor().update(this.carriage.getAirVentilation().getBatteryCharge());
            }
            if (this.carriage.getAirVentilation().getEnergyConsumptionSensor() != null) {
                this.carriage.getAirVentilation().getEnergyConsumptionSensor().update(airVentilationConsumption);
            }
        }
    }

    private void resolveTrashBinsSim(TrashBin bin) {
        if (bin.getBatteryCharge() > 0.0) {
            /*if (this.trashBinsOnOffBufferReaders.get(i).read()) {
                bin.lockOpening();
            } else {
                bin.unlockOpening();
            }*/

            if (bin.getTrashFillPercentageSensor() != null) {
                bin.getTrashFillPercentageSensor().update(bin.getFillPercentage());
            }
            if (bin.getInternalTrashTemperatureSensor() != null) {
                bin.getInternalTrashTemperatureSensor().update(bin.getInternalTemperature());
            }
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
            if (bin.getBatteryChargeSensor() != null) {
                bin.getBatteryChargeSensor().update(bin.getBatteryCharge());
            }
            if (bin.getEnergyConsumptionSensor() != null) {
                bin.getEnergyConsumptionSensor().update(binConsumption);
            }
        }
    }
}
