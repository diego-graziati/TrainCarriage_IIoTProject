package it.unimore.fum.iot.simulation.buffers;

public abstract class PopPushBuffer<T> implements IBuffer {
    public abstract T pop();
    public abstract void push(T item);
}
