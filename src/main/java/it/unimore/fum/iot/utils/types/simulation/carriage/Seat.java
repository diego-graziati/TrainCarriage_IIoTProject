package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

public class Seat {

    private boolean isThePowerOutletOn;
    private boolean isTheLampOn;
    private Passenger seatedPassenger;

    public Seat() {
        this.isThePowerOutletOn = false;
        this.isTheLampOn = false;
        this.seatedPassenger = null;
    }

    public boolean isThePowerOutletOn() {
        return isThePowerOutletOn;
    }

    public void setThePowerOutletOn(boolean thePowerOutletOn) {
        isThePowerOutletOn = thePowerOutletOn;
    }

    public boolean isTheLampOn() {
        return isTheLampOn;
    }

    public void setTheLampOn(boolean theLampOn) {
        isTheLampOn = theLampOn;
    }

    public Passenger getSeatedPassenger() {
        return seatedPassenger;
    }

    public void setSeatedPassenger(Passenger seatedPassenger) {
        this.seatedPassenger = seatedPassenger;
    }
}
