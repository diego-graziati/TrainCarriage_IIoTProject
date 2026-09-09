package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.carriage.*;
import it.unimore.fum.iot.utils.types.simulation.defaults.PassengersSimulationDefaults;
import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;
import it.unimore.fum.iot.utils.types.simulation.passenger.PassengerTasksEnum;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PassengersSimulationModule extends BaseSimulationModule {

    private final ScheduledExecutorService simulationExecutor;
    private final PassengersSimulationDefaults defaults;
    private final Carriage carriage;

    private final List<Passenger> onboardPassengers;

    private Random random;

    public PassengersSimulationModule(Carriage carriage, int updateFrequency) {
        super(new PassengersSimulationDefaults(Paths.Config.Simulation.PASSENGERS), updateFrequency);

        this.defaults = new PassengersSimulationDefaults(Paths.Config.Simulation.PASSENGERS);
        this.carriage = carriage;
        this.onboardPassengers = new ArrayList<>();
        this.random = new Random();
        this.simulationExecutor = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void start() {
        Main.PASSENGERS_SIM_LOGGER.info("Starting PassengersSimulationModule");
        this.simulationExecutor.scheduleAtFixedRate(()->{
            Main.PASSENGERS_SIM_LOGGER.info("Number of onboard passengers: " + this.onboardPassengers.size());

            //RESOLVE ON BOARD PASSENGERS' TASKS. DELETE "LEAVE_CARRIAGE" PASSENGERS (IF POSSIBLE)
            this.onboardPassengers.forEach((passenger) -> {

                boolean passengerLeftCarriage = false;
                if (passenger.getSelectedTask() == PassengerTasksEnum.LEAVE_CARRIAGE) {
                    if (this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.EMBARK) {
                        List<Door> availableDoors;
                        if (this.carriage.getTravelPhase() == CarriageTravelPhasesEnum.DISEMBARK ||
                            this.carriage.getTravelPhase() == CarriageTravelPhasesEnum.COMPLETE_STOP) {
                            availableDoors = this.carriage.getExternalDoors().stream()
                                    .filter(door -> !door.getDoorLock().isDoorLocked()).collect(Collectors.toList());
                        } else {
                            availableDoors = this.carriage.getExternalDoors().stream()
                                    .filter(door -> !door.getDoorLock().isDoorLocked()).collect(Collectors.toList());
                        }

                        if (!availableDoors.isEmpty()) {
                            int selectedDoor = this.random.nextInt(availableDoors.size());

                            availableDoors.get(selectedDoor).openDoor();
                            availableDoors.get(selectedDoor).getPresenceMonitor().incrementOut();
                            passengerLeftCarriage = true;
                        }
                    }
                }
                if (!passengerLeftCarriage) {
                    PassengerTasksEnum nextTask = this.resolvePassengerTask(passenger);
                    passenger.setPreviousTask(passenger.getSelectedTask());
                    passenger.setSelectedTask(nextTask);
                } else {
                    this.onboardPassengers.remove(passenger);
                }
            });

            //DELETE "ILLEGAL" PASSENGERS
            this.onboardPassengers.removeIf(passenger -> passenger.getSelectedTask() == PassengerTasksEnum.ILLEGAL);

            if (this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.EMERGENCY && this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.DISEMBARK) {
                //THEN GENERATE NEW PASSENGERS AND RESOLVE THEIR TASKS
                int numberOfNewPassengers = this.random.nextInt(10);

                if ((numberOfNewPassengers + this.onboardPassengers.size()) >= this.defaults.MAX_NUMBER_OF_ONBOARD_PASSENGERS) {
                    if (this.onboardPassengers.size() < this.defaults.MAX_NUMBER_OF_ONBOARD_PASSENGERS) {
                        numberOfNewPassengers = this.defaults.MAX_NUMBER_OF_ONBOARD_PASSENGERS - this.onboardPassengers.size();
                    } else {
                        numberOfNewPassengers = 0;
                    }
                }
                Main.PASSENGERS_SIM_LOGGER.info("Number of new passengers: " + numberOfNewPassengers);
                List<Passenger> newPassengers = new ArrayList<>(numberOfNewPassengers);
                for (int i = 0; i < numberOfNewPassengers; i++) {
                    newPassengers.add(new Passenger());
                }

                newPassengers.forEach((passenger) -> {
                    PassengerTasksEnum nextTask = this.resolvePassengerTask(passenger);
                    //ILLEGAL PASSENGERS CAN'T HAPPEN HERE. HOWEVER, EVEN IF THAT WERE THE CASE, THEY WOULD BE OVERWRITTEN NEXT CYCLE
                    passenger.setPreviousTask(passenger.getSelectedTask());
                    passenger.setSelectedTask(nextTask);
                });
            }
        }, 0, 60 / super.updateFrequency, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        this.simulationExecutor.shutdown();
    }

    private PassengerTasksEnum resolvePassengerTask(Passenger passenger) {
        PassengerTasksEnum nextTask = PassengerTasksEnum.IDLE;

        Main.PASSENGERS_SIM_LOGGER.info("Resolving passenger task");
        Main.PASSENGERS_SIM_LOGGER.info("Passenger resolving task:\n" + passenger);
        //FIND THE FIRST FREE SEAT, IF ANY IS AVAILABLE, AS IT IS USED TO GET THE BOOLEAN "areThereEmptySeats"
        Seat freeSeat = this.carriage.getSeats().stream()
                            .filter(seat -> seat.getSeatedPassenger() == null)
                            .findFirst()
                            .orElse(null);
        Main.PASSENGERS_SIM_LOGGER.info("Free seat: " + freeSeat);
        Seat passengerSeat = this.carriage.getSeats().stream()
                                .filter(seat -> seat.getSeatedPassenger() != null &&
                                        seat.getSeatedPassenger().getUuid() == passenger.getUuid())
                                .findFirst()
                                .orElse(null);
        Main.PASSENGERS_SIM_LOGGER.info("Passenger seat: " + passengerSeat);
        Toilet freeToilet = this.carriage.getToilets().stream()
                                .filter(toilet -> !toilet.isOccupied())
                                .findFirst()
                                .orElse(null);
        Main.PASSENGERS_SIM_LOGGER.info("Free toilet: " + freeToilet);
        List<Door> availableDoors;
        boolean areThereEmptySeats = freeSeat != null;
        boolean isTheToiletEmpty = freeToilet != null;
        boolean isPowerOutletAvailable = (passengerSeat != null) && (!passengerSeat.getPowerOutlet().isOccupied());
        boolean isLampAvailable = (passengerSeat != null) && (!passengerSeat.getLamp().isOn());

        switch (passenger.getSelectedTask()) {
            case ENTER_CARRIAGE:
                if (this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.DISEMBARK ||
                        this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.COMPLETE_STOP) {
                    if (this.carriage.getTravelPhase() == CarriageTravelPhasesEnum.EMBARK) {
                        availableDoors = this.carriage.getExternalDoors().stream()
                                .filter(door -> !door.getDoorLock().isDoorLocked()).collect(Collectors.toList());
                    } else {
                        availableDoors = this.carriage.getInternalDoors().stream()
                                .filter(door -> !door.getDoorLock().isDoorLocked()).collect(Collectors.toList());
                    }

                    if (!availableDoors.isEmpty()) {
                        int selectedDoor = this.random.nextInt(availableDoors.size());

                        availableDoors.get(selectedDoor).openDoor();
                        availableDoors.get(selectedDoor).getPresenceMonitor().incrementIn();

                        nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                    } else {
                        nextTask = PassengerTasksEnum.ENTER_CARRIAGE;
                    }
                } else {
                    nextTask = PassengerTasksEnum.LEAVE_CARRIAGE;
                }


                break;

            case LEAVE_CARRIAGE:
                if (this.carriage.getTravelPhase() != CarriageTravelPhasesEnum.EMBARK) {
                    nextTask = PassengerTasksEnum.LEAVE_CARRIAGE;
                } else {
                    nextTask = PassengerTasksEnum.ENTER_CARRIAGE;
                }

                break;

            case SIT:
                if(areThereEmptySeats){
                    freeSeat.setSeatedPassenger(passenger);
                }

                nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                break;

            case GET_UP:
                if(passengerSeat != null){
                    passengerSeat.setSeatedPassenger(null);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("Passenger already left the seat!");
                }

                nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                break;

            case IDLE:
                nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                break;

            case ENTER_TOILET:
                if (isTheToiletEmpty) {
                    freeToilet.setPassenger(passenger);
                    nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                } else {
                    nextTask = PassengerTasksEnum.ENTER_TOILET;
                }
                break;

            case LEAVE_TOILET:
                Toilet passengerToilet = this.carriage.getToilets().stream()
                                            .filter(toilet -> toilet.getPassenger() != null &&
                                                    toilet.getPassenger().getUuid() == passenger.getUuid())
                                            .findFirst()
                                            .orElse(null);
                if (passengerToilet != null) {
                    passengerToilet.setPassenger(null);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("Passenger already left the toilet!");
                }

                nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                break;

            case CONNECT_TO_POWER_OUTLET:
                if (passengerSeat != null) {

                    if (!isPowerOutletAvailable) {
                        passengerSeat.getPowerOutlet().connect();
                    } else {
                        Main.PASSENGERS_SIM_LOGGER.warning("Seat power outlet already on!");
                    }

                    nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("The passenger isn't seated!");

                    nextTask = PassengerTasksEnum.ILLEGAL;
                }
                break;

            case DISCONNECT_FROM_POWER_OUTLET:
                if (passengerSeat != null) {

                    if (isPowerOutletAvailable) {
                        passengerSeat.getPowerOutlet().disconnect();
                    } else {
                        Main.PASSENGERS_SIM_LOGGER.warning("Seat power outlet already off!");
                    }

                    nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("The passenger isn't seated!");

                    nextTask = PassengerTasksEnum.ILLEGAL;
                }
                break;

            case TURN_SEAT_LAMP_ON:
                if (passengerSeat != null) {

                    if (isLampAvailable) {
                        passengerSeat.getLamp().turnOn();
                    } else {
                        Main.PASSENGERS_SIM_LOGGER.warning("Seat lamp already on!");
                    }

                    nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("The passenger isn't seated!");

                    nextTask = PassengerTasksEnum.ILLEGAL;
                }
                break;

            case TURN_SEAT_LAMP_OFF:
                if (passengerSeat != null) {

                    if (!isLampAvailable) {
                        passengerSeat.getLamp().turnOff();
                    } else {
                        Main.PASSENGERS_SIM_LOGGER.warning("Seat lamp already off!");
                    }

                    nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletAvailable, isLampAvailable);
                } else {
                    Main.PASSENGERS_SIM_LOGGER.warning("The passenger isn't seated!");

                    nextTask = PassengerTasksEnum.ILLEGAL;
                }
                break;
        }

        if (nextTask == PassengerTasksEnum.ILLEGAL) {
            if (passengerSeat != null) {
                passengerSeat.setSeatedPassenger(null);
            }
            if (freeSeat != null) {
                freeSeat.setSeatedPassenger(null);
            }
            if (freeToilet != null) {
                freeToilet.setPassenger(null);
            }
        }

        return nextTask;
    }

    private PassengerTasksEnum nextPassengerTask(PassengerTasksEnum currentTask, boolean areThereEmptySeats,
                                                 boolean isTheToiletEmpty, boolean isPowerOutletUsed, boolean isLampOff) {
        Set<PassengerTasksEnum> nextTaskPool = EnumSet.noneOf(PassengerTasksEnum.class);

        if (this.carriage.getTravelPhase() == CarriageTravelPhasesEnum.EMERGENCY)
        {
            nextTaskPool.add(PassengerTasksEnum.LEAVE_CARRIAGE);
        } else {
            if (currentTask == PassengerTasksEnum.ENTER_CARRIAGE) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.ENTER_TOILET, PassengerTasksEnum.SIT, PassengerTasksEnum.LEAVE_CARRIAGE));
                if (!isTheToiletEmpty) {
                    nextTaskPool.remove(PassengerTasksEnum.ENTER_TOILET);
                }
                if(!areThereEmptySeats){
                    nextTaskPool.remove(PassengerTasksEnum.SIT);
                }
            }

            if (currentTask == PassengerTasksEnum.SIT) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.IDLE, PassengerTasksEnum.GET_UP));
            }

            if (currentTask == PassengerTasksEnum.IDLE) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.GET_UP, PassengerTasksEnum.CONNECT_TO_POWER_OUTLET,
                        PassengerTasksEnum.DISCONNECT_FROM_POWER_OUTLET, PassengerTasksEnum.TURN_SEAT_LAMP_ON,
                        PassengerTasksEnum.TURN_SEAT_LAMP_OFF));

                if (isPowerOutletUsed) {
                    nextTaskPool.remove(PassengerTasksEnum.DISCONNECT_FROM_POWER_OUTLET);
                } else {
                    nextTaskPool.remove(PassengerTasksEnum.CONNECT_TO_POWER_OUTLET);
                }

                if (isLampOff) {
                    nextTaskPool.remove(PassengerTasksEnum.TURN_SEAT_LAMP_OFF);
                } else {
                    nextTaskPool.remove(PassengerTasksEnum.TURN_SEAT_LAMP_ON);
                }
            }

            if (currentTask == PassengerTasksEnum.GET_UP) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.SIT, PassengerTasksEnum.ENTER_TOILET, PassengerTasksEnum.LEAVE_CARRIAGE));

                if (!areThereEmptySeats) {
                    nextTaskPool.remove(PassengerTasksEnum.SIT);
                }

                if (!isTheToiletEmpty) {
                    nextTaskPool.remove(PassengerTasksEnum.ENTER_TOILET);
                }
            }

            if (currentTask == PassengerTasksEnum.ENTER_TOILET) {
                nextTaskPool.add(PassengerTasksEnum.LEAVE_TOILET);
            }

            if (currentTask == PassengerTasksEnum.CONNECT_TO_POWER_OUTLET) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.IDLE, PassengerTasksEnum.DISCONNECT_FROM_POWER_OUTLET));
            }

            if (currentTask == PassengerTasksEnum.DISCONNECT_FROM_POWER_OUTLET) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.IDLE, PassengerTasksEnum.CONNECT_TO_POWER_OUTLET));
            }

            if (currentTask == PassengerTasksEnum.TURN_SEAT_LAMP_ON) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.IDLE, PassengerTasksEnum.TURN_SEAT_LAMP_OFF));
            }

            if (currentTask == PassengerTasksEnum.TURN_SEAT_LAMP_OFF) {
                nextTaskPool.addAll(EnumSet.of(PassengerTasksEnum.IDLE, PassengerTasksEnum.TURN_SEAT_LAMP_ON));
            }
        }

        PassengerTasksEnum[] nextTasksFinalPoll = nextTaskPool.toArray(new PassengerTasksEnum[0]);
        return nextTasksFinalPoll[this.random.nextInt(nextTasksFinalPoll.length)];
    }
}
