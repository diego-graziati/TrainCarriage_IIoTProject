package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

public class Seat {

    private Passenger seatedPassenger;
    private final PowerOutlet powerOutlet;
    private final Lamp lamp;

    public Seat() {
        this.seatedPassenger = null;
        this.powerOutlet = new PowerOutlet(Paths.Config.Carriage.Seat.POWER_OUTLET);
        this.lamp = new Lamp(Paths.Config.Carriage.Seat.LAMP);
    }

    public PowerOutlet getPowerOutlet() {
        return powerOutlet;
    }

    public Lamp getLamp() {
        return lamp;
    }

    public Passenger getSeatedPassenger() {
        return seatedPassenger;
    }

    public void setSeatedPassenger(Passenger seatedPassenger) {
        this.seatedPassenger = seatedPassenger;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatedPassenger=" + seatedPassenger +
                ", powerOutlet=" + powerOutlet +
                ", lamp=" + lamp +
                '}';
    }
}
