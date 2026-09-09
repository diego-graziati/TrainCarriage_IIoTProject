package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface ITrashBin {
    public double getFillPercentage();
    public void setFillPercentage(double fillPercentage);
    public double getInternalTemperature();
    public void setInternalTemperature(double internalTemperature);
    public boolean isOpeningLocked();
    public void lockOpening();
    public void unlockOpening();
}
