package it.unimore.fum.iot.simulation.threads;

import it.unimore.fum.iot.simulation.buffers.BufferedWriter;
import it.unimore.fum.iot.simulation.modules.IBaseSimulationModule;

public class BufferedWriterThread<T> extends Thread {

    private final BufferedWriter<T> bufferedWriter;

    public BufferedWriterThread(BufferedWriter<T> writer, IBaseSimulationModule simModule, int writingRate) {
        this.bufferedWriter = writer;
    }

    @Override
    public void run() {
        super.run();


    }
}
