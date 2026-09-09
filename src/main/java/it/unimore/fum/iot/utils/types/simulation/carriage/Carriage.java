package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.defaults.CarriageDefaults;

import java.util.ArrayList;
import java.util.List;

public class Carriage {

    private CarriageTravelPhasesEnum travelPhases;
    private final List<Seat> seats;
    private final List<Toilet> toilets;
    private final List<Light> carriageLights;
    private final List<Door> externalDoors;
    private final List<Door> internalDoors;
    private final AirVentilation airVentilation;
    private final List<TrashBin> trashBins;

    private final CarriageDefaults defaults;

    public Carriage() {
        this.defaults = new CarriageDefaults(Paths.Config.Carriage.CARRIAGE);

        this.travelPhases = CarriageTravelPhasesEnum.EMBARK;
        this.seats = new ArrayList<>(this.defaults.NUM_SEATS);
        for (int i = 0; i < this.defaults.NUM_SEATS; i++) {
            this.seats.add(new Seat());
        }
        this.toilets = new ArrayList<>(this.defaults.NUM_TOILETS);
        for (int i = 0; i < this.defaults.NUM_TOILETS; i++) {
            this.toilets.add(new Toilet());
        }
        this.carriageLights = new ArrayList<>(this.defaults.NUM_LIGHTS);
        for (int i = 0; i < this.defaults.NUM_LIGHTS; i++) {
            this.carriageLights.add(new Light(Paths.Config.Carriage.LIGHTS));
        }

        this.externalDoors = new ArrayList<>(this.defaults.NUM_EXTERNAL_DOORS);
        for (int i = 0; i < this.defaults.NUM_EXTERNAL_DOORS; i++) {
            this.externalDoors.add(new Door(Paths.Config.Carriage.DOORS));
        }
        this.internalDoors = new ArrayList<>(this.defaults.NUM_INTERNAL_DOORS);
        for (int i = 0; i < this.defaults.NUM_INTERNAL_DOORS; i++) {
            this.internalDoors.add(new Door(Paths.Config.Carriage.DOORS));
        }
        this.airVentilation = new AirVentilation(Paths.Config.Carriage.AIR_VENTILATION);
        this.trashBins = new ArrayList<>(this.defaults.NUM_TRASH_BINS);
        for (int i = 0; i < this.defaults.NUM_TRASH_BINS; i++) {
            this.trashBins.add(new TrashBin(Paths.Config.Carriage.TRASH_BINS));
        }
    }

    public CarriageTravelPhasesEnum getTravelPhase() {
        return travelPhases;
    }

    public void setTravelPhase(CarriageTravelPhasesEnum travelPhases) {
        this.travelPhases = travelPhases;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Toilet> getToilets() {
        return toilets;
    }

    public List<Light> getCarriageLights() {
        return carriageLights;
    }

    public List<Door> getExternalDoors() {
        return externalDoors;
    }

    public List<Door> getInternalDoors() {
        return internalDoors;
    }

    public AirVentilation getAirVentilation() {
        return airVentilation;
    }

    public List<TrashBin> getTrashBins() {
        return trashBins;
    }
}
