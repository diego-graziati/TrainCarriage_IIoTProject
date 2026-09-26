package it.unimore.fum.iot.utils.tools.buffers;

public class BufferWriter<T> {

    private final ReadWriteBuffer<T> readWriteBuffer;

    public BufferWriter(ReadWriteBuffer<T> readWriteBuffer) {
        this.readWriteBuffer = readWriteBuffer;
    }

    public void write(T item) {
        readWriteBuffer.write(item);
    }

    @Override
    public String toString() {
        return "BufferWriter{" +
                "readWriteBuffer=" + readWriteBuffer +
                '}';
    }
}
