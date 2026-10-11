package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface ITrashBin {
    double getFillPercentage();
    void setFillPercentage(double fillPercentage);
    double getInternalTemperature();
    void setInternalTemperature(double internalTemperature);
    boolean isOpeningLocked();
    void lockOpening();
    void unlockOpening();
}
