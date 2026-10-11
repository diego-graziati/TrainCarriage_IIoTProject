package it.unimore.fum.iot.utils.tools.buffers;

public abstract class PopPushBuffer<T> implements IBuffer {
    public abstract T pop();
    public abstract void push(T item);
}
