package it.unimore.fum.iot.utils.types.drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimulationSensorDriver<T> implements SensorDriver<T> {

    private T currentData;
    private final List<Consumer<T>> listeners;

    public SimulationSensorDriver() {
        this.listeners = new ArrayList<>();
    }

    public void update(T newValue) {
        this.currentData = newValue;
        for (Consumer<T> listener : this.listeners) {
            listener.accept(newValue);
        }
    }

    @Override
    public T read() {
        return this.currentData;
    }

    @Override
    public void registerListeners(Consumer<T> listener) {
        this.listeners.add(listener);
    }
}
