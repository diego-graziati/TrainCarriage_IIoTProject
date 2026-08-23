package it.unimore.fum.iot.simulation.buffers;

public class SingleItemReadWriteBuffer<T> extends ReadWriteBuffer<T> {

    private T item;

    public SingleItemReadWriteBuffer() {

    }

    @Override
    public T read() {
        return item;
    }

    @Override
    public void write(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "SingleItemReadWriteBuffer{" +
                "item=" + item +
                '}';
    }
}
