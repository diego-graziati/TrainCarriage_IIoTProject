package it.unimore.fum.iot.simulation;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.simulation.simmodules.CarriageSimulationModule;
import it.unimore.fum.iot.simulation.simmodules.PassengersSimulationModule;
import it.unimore.fum.iot.simulation.simmodules.TemperatureHumiditySimulationModule;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;

public class SimulationOrchestrator {

    private final TemperatureHumiditySimulationModule temperatureHumiditySimulationModule;
    private final PassengersSimulationModule passengersSimulationModule;
    private final CarriageSimulationModule carriageSimulationModule;

    public SimulationOrchestrator(Carriage carriage) {
        //TESTING IF SIMULATION IS WORKING
        Main.MAIN_LOGGER.info("Simulations initialization");
        int updateFrequency = 60;

        registerCarriageSimActuatorsListeners(carriage);
        registerTemperatureHumiditySimActuatorsListeners(carriage);
        this.temperatureHumiditySimulationModule = new TemperatureHumiditySimulationModule(carriage, updateFrequency);
        this.passengersSimulationModule = new PassengersSimulationModule(carriage, updateFrequency);
        this.carriageSimulationModule = new CarriageSimulationModule(carriage, updateFrequency);

        Main.MAIN_LOGGER.info("All simulations have been successfully initialized");
    }

    public void start() {
        Main.MAIN_LOGGER.info("Starting simulations");
        this.temperatureHumiditySimulationModule.start();
        this.passengersSimulationModule.start();
        this.carriageSimulationModule.start();
        Main.MAIN_LOGGER.info("All simulations have been successfully started");
    }

    public void stop() {
        Main.MAIN_LOGGER.info("Stopping simulations");
        this.temperatureHumiditySimulationModule.stop();
        this.passengersSimulationModule.stop();
        this.carriageSimulationModule.stop();
        Main.MAIN_LOGGER.info("All simulations have been successfully stopped");
    }

    private void registerCarriageSimActuatorsListeners(Carriage carriage) {
        //DOORS ACTUATORS:
        for (Door door: carriage.getExternalDoors()) {
            door.getDoorLock().getDoorLockActuator().registerListeners(command -> {
                if (door.getDoorLock().getBatteryCharge() > 0.0) {
                    if (command) {
                        door.getDoorLock().lockDoor();
                    } else {
                        door.getDoorLock().unlockDoor();
                    }
                } else {
                    door.getDoorLock().unlockDoor();
                }
            });
        }

        for (Door door: carriage.getInternalDoors()) {
            door.getDoorLock().getDoorLockActuator().registerListeners(command -> {
                if (door.getDoorLock().getBatteryCharge() > 0.0) {
                    if (command) {
                        door.getDoorLock().lockDoor();
                    } else {
                        door.getDoorLock().unlockDoor();
                    }
                } else {
                    door.getDoorLock().unlockDoor();
                }
            });
        }

        //CARRIAGE LIGHTS ACTUATORS:
        for (Light light: carriage.getCarriageLights()) {
            light.getTurnLightsOnOffActuator().registerListeners(command -> {
                if (light.getBatteryCharge() > 0.0) {
                    if (command) {
                        light.turnLightsOn();
                    } else {
                        light.turnLightsOff();
                    }
                } else {
                    light.turnLightsOff();
                }
            });
        }

        //TOILETS LIGHTS ACTUATORS
        for (Toilet toilet: carriage.getToilets()) {
            toilet.getToiletLights().getTurnLightsOnOffActuator().registerListeners(command -> {
                if (toilet.getToiletLights().getBatteryCharge() > 0.0) {
                    if (command) {
                        toilet.getToiletLights().turnLightsOn();
                    } else {
                        toilet.getToiletLights().turnLightsOff();
                    }
                } else {
                    toilet.getToiletLights().turnLightsOff();
                }
            });
        }

        //SEATS ACTUATORS:
        for (Seat seat: carriage.getSeats()) {
            seat.getLamp().getTurnOnOffActuator().registerListeners(command -> {
                if (seat.getLamp().getBatteryCharge() > 0.0) {
                    if (command) {
                        seat.getLamp().turnOn();
                    } else {
                        seat.getLamp().turnOff();
                    }
                } else {
                    seat.getLamp().turnOff();
                }
            });

            seat.getPowerOutlet().getCutoffPowerOutletActuator().registerListeners(command -> {
                if (seat.getPowerOutlet().getBatteryCharge() > 0.0) {
                    if (command) {
                        seat.getPowerOutlet().cutoff();
                    } else {
                        seat.getPowerOutlet().repair();
                    }
                }
            });
        }

        //AIR VENTILATION ACTUATORS:
        carriage.getAirVentilation().getAirVentsActuator().registerListeners(command -> {
            if (carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                if (command) {
                    carriage.getAirVentilation().openAirVents();
                } else {
                    carriage.getAirVentilation().closeAirVents();
                }
            } else {
                carriage.getAirVentilation().openAirVents();
            }
        });

        carriage.getAirVentilation().getAirVentilationActuator().registerListeners(command -> {
            if (carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                if (command) {
                    carriage.getAirVentilation().turnAirVentilationOn();
                } else {
                    carriage.getAirVentilation().turnAirVentilationOff();
                }
            } else {
                carriage.getAirVentilation().turnAirVentilationOff();
            }
        });

        carriage.getAirVentilation().getDehumidifierActuator().registerListeners(command -> {
            if (carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                if (command) {
                    carriage.getAirVentilation().turnDehumidifierOn();
                } else {
                    carriage.getAirVentilation().turnDehumidifierOff();
                }
            } else {
                carriage.getAirVentilation().turnDehumidifierOff();
            }
        });

        //TRASH BINS ACTUATORS:
        for (TrashBin bin: carriage.getTrashBins()) {
            bin.getTrashBinLockActuator().registerListeners(command -> {
                if (bin.getBatteryCharge() > 0.0) {
                    if (command) {
                        bin.lockOpening();
                    } else {
                        bin.unlockOpening();
                    }
                } else {
                    bin.unlockOpening();
                }
            });
        }
    }

    private void registerTemperatureHumiditySimActuatorsListeners(Carriage carriage) {

        carriage.getAirVentilation().getTargetAirTemperatureActuator().registerListeners(command -> {
            if (carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                carriage.getAirVentilation().setTargetAirTemperature(command);
            }
        });

        carriage.getAirVentilation().getTargetHumidityActuator().registerListeners(command -> {
            if (carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                carriage.getAirVentilation().setTargetHumidity(command);
            }
        });
    }
}
