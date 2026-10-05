package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface IPowerOutlet {
    boolean isOccupied();
    void connect();
    void disconnect();
}
