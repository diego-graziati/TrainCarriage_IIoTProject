package it.unimore.fum.iot.utils.types.drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimulationActuatorDriver<T> implements ActuatorDriver<T> {

    private T command;
    private final List<Consumer<T>> listeners;

    public SimulationActuatorDriver() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void registerListeners(Consumer<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void execute(T command) {
        this.command = command;
        for (Consumer<T> listener : this.listeners) {
            listener.accept(command);
        }
    }
}
