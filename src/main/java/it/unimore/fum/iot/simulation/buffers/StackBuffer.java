package it.unimore.fum.iot.simulation.buffers;

import java.util.ArrayList;
import java.util.List;

public class StackBuffer<T> extends PopPushBuffer<T> {

    private final List<T> buffer;

    public StackBuffer(int max_buffer_size) {
        buffer = new ArrayList<>(max_buffer_size);
    }

    @Override
    public synchronized T pop() {
        return buffer.remove(0);
    }

    @Override
    public synchronized void push(T value) {
        buffer.add(value);
    }
}
