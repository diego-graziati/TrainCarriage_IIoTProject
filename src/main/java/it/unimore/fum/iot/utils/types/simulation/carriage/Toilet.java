package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

public class Toilet {

    private boolean isOccupied;
    private Passenger passenger;

    public Toilet() {
        this.isOccupied = false;
        this.passenger = null;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    private void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
        this.setOccupied(passenger != null);
    }
}
