package it.unimore.fum.iot.models;

public class PresenceMonitoringSensorModel {
    private long timestamp;
    private int in;
    private int out;

    //TODO: values should be obtained through a simulation and config files, not fixed values!
    public PresenceMonitoringSensorModel() {
        this.in = 1;
        this.out = 1;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PresenceMonitoringSensorModel{" +
                "in=" + in +
                ", out=" + out +
                ", timestamp=" + timestamp +
                '}';
    }
}
