package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;
import it.unimore.fum.iot.utils.types.simulation.carriage.Seat;
import it.unimore.fum.iot.utils.types.simulation.defaults.PassengersSimulationDefaults;
import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;
import it.unimore.fum.iot.utils.types.simulation.passenger.PassengerTasksEnum;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PassengersSimulationModule extends BaseSimulationModule {

    private final ScheduledExecutorService simulationExecutor;
    private final Logger logger;
    private final PassengersSimulationDefaults defaults;
    private final Carriage carriage;

    private final List<Passenger> onboardPassengers;

    private Random random;

    public PassengersSimulationModule(PassengersSimulationDefaults defaults, Carriage carriage, int updateFrequency) {
        super(defaults, updateFrequency);

        this.logger = Logger.getLogger(PassengersSimulationModule.class.getName());
        this.defaults = defaults;
        this.carriage = carriage;
        this.onboardPassengers = new ArrayList<>();
        this.random = new Random();
        this.simulationExecutor = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void start() {
        this.simulationExecutor.scheduleAtFixedRate(()->{
            //TODO: while each case could be similar, the way they handle the doors, the toilet and all other things differs!
            //  all is considered complete ONLY when everything has been accounted for!
            switch (this.carriage.getTravelPhases()) {
                case EMBARK:
                    this.logger.info("EMBARK!");

                    //FIRST, RESOLVE ON BOARD PASSENGERS' TASKS
                    this.onboardPassengers.forEach((passenger) -> {
                        PassengerTasksEnum nextTask = this.resolvePassengerTask(passenger);
                        passenger.setPreviousTask(passenger.getSelectedTask());
                        passenger.setSelectedTask(nextTask);
                    });

                    //THEN GENERATE NEW PASSENGERS AND RESOLVE THEIR TASKS
                    int numberOfNewPassengers = this.random.nextInt(10);
                    List<Passenger> newPassengers = new ArrayList<>(numberOfNewPassengers);
                    for (int i = 0; i < numberOfNewPassengers; i++) {
                        newPassengers.add(new Passenger());
                    }

                    newPassengers.forEach((passenger) -> {
                        PassengerTasksEnum nextTask = this.resolvePassengerTask(passenger);
                        passenger.setPreviousTask(passenger.getSelectedTask());
                        passenger.setSelectedTask(nextTask);
                    });
                    break;
                case BEGIN_TRAVEL:
                    this.logger.info("BEGIN_TRAVEL!");
                    break;
                case TRAVEL:
                    this.logger.info("TRAVEL!");
                    break;
                case END_TRAVEL:
                    this.logger.info("END_TRAVEL!");
                    break;
                case DISEMBARK:
                    this.logger.info("DISEMBARK!");
                    break;
                default:
                    this.logger.info("EMERGENCY!");
                    break;
            }
        }, 0, 60 / super.updateFrequency, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        this.simulationExecutor.shutdown();
    }

    private PassengerTasksEnum resolvePassengerTask(Passenger passenger) {
        PassengerTasksEnum nextTask = PassengerTasksEnum.IDLE;

        //FIND THE FIRST FREE SEAT, IF ANY IS AVAILABLE, AS IT IS USED TO GET THE BOOLEAN "areThereEmptySeats"
        Seat freeSeat = this.carriage.getSeats().stream()
                            .filter(seat -> seat.getSeatedPassenger() == null)
                            .findFirst()
                            .orElse(null);
        boolean areThereEmptySeats = freeSeat != null;
        boolean isTheToiletEmpty = true;
        boolean isPowerOutletUsed = true;
        boolean isLampOff = true;

        switch (passenger.getSelectedTask()) {
            case ENTER_CARRIAGE:
                this.onboardPassengers.add(passenger);

                nextTask = nextPassengerTask(passenger.getSelectedTask(), areThereEmptySeats, isTheToiletEmpty, isPowerOutletUsed, isLampOff);
                break;

            case LEAVE_CARRIAGE:
                this.onboardPassengers.remove(passenger);
                break;

            case SIT:
                if(areThereEmptySeats){
                    freeSeat.setSeatedPassenger(passenger);
                }
                break;
        }

        return nextTask;
    }

    private PassengerTasksEnum nextPassengerTask(PassengerTasksEnum currentTask, boolean areThereEmptySeats,
                                                 boolean isTheToiletEmpty, boolean isPowerOutletUsed, boolean isLampOff) {
        Set<PassengerTasksEnum> nextTaskPool = EnumSet.noneOf(PassengerTasksEnum.class);

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

        PassengerTasksEnum[] nextTasksFinalPoll = nextTaskPool.toArray(new PassengerTasksEnum[0]);
        return nextTasksFinalPoll[this.random.nextInt(nextTasksFinalPoll.length)];
    }
}
