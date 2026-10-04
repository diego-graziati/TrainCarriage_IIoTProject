package it.unimore.fum.iot.utils.types.drivers;

import java.util.function.Consumer;

public interface SensorDriver<T> {
    T read();
    void registerListeners(Consumer<T> listener);
}
