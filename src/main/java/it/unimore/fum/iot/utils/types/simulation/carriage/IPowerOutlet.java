package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface IPowerOutlet {
    public boolean isOccupied();
    public void connect();
    public void disconnect();
}
