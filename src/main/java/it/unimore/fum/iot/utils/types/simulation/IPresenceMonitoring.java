package it.unimore.fum.iot.utils.types.simulation;

public interface IPresenceMonitoring {
    int getIn();
    int getOut();
    void incrementIn();
    void incrementOut();
}
