package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.defaults.LightDefaults;
import it.unimore.fum.iot.utils.types.simulation.passenger.Passenger;

public class Toilet {

    private boolean isOccupied;
    private final Light toiletLights;
    private Passenger passenger;

    public Toilet() {
        this.isOccupied = false;
        this.passenger = null;
        this.toiletLights = new Light(Paths.Config.Carriage.Toilet.LIGHTS);
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

    public Light getToiletLights() {
        return toiletLights;
    }

    @Override
    public String toString() {
        return "Toilet{" +
                "isOccupied=" + isOccupied +
                ", toiletLights=" + toiletLights +
                ", passenger=" + passenger +
                '}';
    }
}
