package it.unimore.fum.iot.simulation.buffers;

public abstract class ReadWriteBuffer<T> implements IBuffer {
    public abstract T read();
    public abstract void write(T item);
}
