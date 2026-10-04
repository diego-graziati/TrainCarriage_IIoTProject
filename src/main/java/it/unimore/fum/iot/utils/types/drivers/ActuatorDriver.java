package it.unimore.fum.iot.utils.types.drivers;

import java.util.function.Consumer;

public interface ActuatorDriver<T> {
    void registerListeners(Consumer<T> listener);
    void execute(T command);
}
