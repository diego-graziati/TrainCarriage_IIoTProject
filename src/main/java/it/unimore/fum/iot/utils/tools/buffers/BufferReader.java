package it.unimore.fum.iot.utils.tools.buffers;

public class BufferReader<T> {
    private final ReadWriteBuffer<T> readWriteBuffer;

    public BufferReader(ReadWriteBuffer<T> readWriteBuffer) {
        this.readWriteBuffer = readWriteBuffer;
    }

    public T read() {
        return readWriteBuffer.read();
    }

    @Override
    public String toString() {
        return "BufferWriter{" +
                "readWriteBuffer=" + readWriteBuffer +
                '}';
    }
}
