package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

public class Carriage {

    private CarriageTravelPhasesEnum travelPhases;
    private final List<Seat> seats;

    public Carriage(int numberOfSeats) {
        this.travelPhases = CarriageTravelPhasesEnum.EMBARK;
        this.seats = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            this.seats.add(new Seat());
        }
    }

    public CarriageTravelPhasesEnum getTravelPhases() {
        return travelPhases;
    }

    public void setTravelPhases(CarriageTravelPhasesEnum travelPhases) {
        this.travelPhases = travelPhases;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
