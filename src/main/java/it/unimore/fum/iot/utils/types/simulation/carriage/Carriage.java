package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

public class Carriage {

    private CarriageTravelPhasesEnum travelPhases;
    private final List<Seat> seats;
    private final List<Toilet> toilets;

    public Carriage(int numberOfSeats, int numberOfToilets) {
        this.travelPhases = CarriageTravelPhasesEnum.EMBARK;
        this.seats = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            this.seats.add(new Seat());
        }
        this.toilets = new ArrayList<>(numberOfToilets);
        for (int i = 0; i < numberOfToilets; i++) {
            this.toilets.add(new Toilet());
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

    public List<Toilet> getToilets() {
        return toilets;
    }
}
