package it.unimore.fum.iot.utils.types.simulation;

public interface IPresenceMonitoring {
    public int getIn();
    public int getOut();
    public void incrementIn();
    public void incrementOut();
}
