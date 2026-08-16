package it.unimore.fum.iot.simulation.buffers;

public class BufferedWriter<T> {

    private final ReadWriteBuffer<T> readWriteBuffer;

    public BufferedWriter(ReadWriteBuffer<T> readWriteBuffer) {
        this.readWriteBuffer = readWriteBuffer;
    }

    public void write(T item) {
        readWriteBuffer.write(item);
    }
}
