package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.tools.ModelStateChangeNotifier;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.javatuples.Pair;

public class PresenceMonitoringSensorModel {
    private long timestamp;
    private int in;
    private int out;

    private ModelStateChangeNotifier listener;

    public PresenceMonitoringSensorModel(SensorDriver<Pair<Integer, Integer>> presenceMonitoringSensor) {
        this.in = 1;
        this.out = 1;

        presenceMonitoringSensor.registerListeners(val -> {
            this.in = val.getValue0();
            this.out = val.getValue1();

            if (this.listener != null) {
                this.listener.onStateChange();
            }
        });
    }

    public void setOnStateChange(ModelStateChangeNotifier listener) {
        this.listener = listener;
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
