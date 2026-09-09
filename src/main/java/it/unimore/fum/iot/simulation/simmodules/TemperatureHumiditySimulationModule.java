package it.unimore.fum.iot.simulation.simmodules;

import it.unimore.fum.iot.Main;
import it.unimore.fum.iot.simulation.buffers.BufferWriter;
import it.unimore.fum.iot.simulation.buffers.SingleItemReadWriteBuffer;
import it.unimore.fum.iot.utils.types.collectors.TemperatureHumidityBuffersCollector;
import it.unimore.fum.iot.utils.types.simulation.Paths;
import it.unimore.fum.iot.utils.types.simulation.carriage.Carriage;
import it.unimore.fum.iot.utils.types.simulation.defaults.TemperatureSimulationDefaults;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TemperatureHumiditySimulationModule extends BaseSimulationModule {

    private double temperature;
    private double humidity;
    private final ScheduledExecutorService scheduler;
    private final TemperatureSimulationDefaults defaults;
    private final Carriage carriage;

    private final TemperatureHumidityBuffersCollector buffers;

    public TemperatureHumiditySimulationModule(Carriage carriage, TemperatureHumidityBuffersCollector buffers, int updateFrequency) {
        super(new TemperatureSimulationDefaults(Paths.Config.Simulation.TEMPERATURE), updateFrequency);

        this.defaults = new TemperatureSimulationDefaults(Paths.Config.Simulation.TEMPERATURE);
        this.temperature = defaults.INITIAL_TEMPERATURE;
        this.humidity = defaults.INITIAL_HUMIDITY;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.carriage = carriage;
        this.buffers = buffers;

    }

    @Override
    public void start() {
        this.scheduler.scheduleAtFixedRate(() -> {
            AtomicInteger numDoorsOpen = new AtomicInteger();
            this.carriage.getExternalDoors().forEach(door -> {
                if(door.isDoorOpen()) {
                    numDoorsOpen.addAndGet(1);
                }
            });

            double doorsOpenTemperatureModifier = 0.0;
            double doorsOpenHumidityModifier = 0.0;
            if (this.defaults.EXTERIORS_TEMPERATURE > this.temperature) {
                doorsOpenTemperatureModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER;
                doorsOpenHumidityModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_HUMIDITY_MODIFIER;
            } else if (this.defaults.EXTERIORS_TEMPERATURE < this.temperature) {
                doorsOpenTemperatureModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER * (-1);
                doorsOpenHumidityModifier = numDoorsOpen.get() * this.defaults.DOOR_OPEN_HUMIDITY_MODIFIER * (-1);
            }

            double airVentilationTemperatureModifier = 0.0;
            double airVentilationHumidityModifier = 0.0;
            if (this.carriage.getAirVentilation().areAirVentsOpen()) {
                if (this.carriage.getAirVentilation().isAirVentilationOn()) {
                    if (this.carriage.getAirVentilation().getTargetAirTemperature() > this.temperature) {
                        airVentilationTemperatureModifier = this.carriage.getAirVentilation().getAirVentilationModifier()
                                * (this.carriage.getAirVentilation().getAirVentilationEfficiency())/100.0;
                    } else if (this.carriage.getAirVentilation().getTargetAirTemperature() < this.temperature) {
                        airVentilationTemperatureModifier = this.carriage.getAirVentilation().getAirVentilationModifier()
                                * (this.carriage.getAirVentilation().getAirVentilationEfficiency())/100.0 * (-1);
                    }
                } else {
                    if (this.defaults.EXTERIORS_TEMPERATURE > this.temperature) {
                        airVentilationTemperatureModifier = this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER;
                    } else if (this.defaults.EXTERIORS_TEMPERATURE < this.temperature) {
                        airVentilationTemperatureModifier = this.defaults.DOOR_OPEN_TEMPERATURE_MODIFIER * (-1);
                    }
                }

                if (this.carriage.getAirVentilation().isDehumidifierOn()) {
                    if (this.carriage.getAirVentilation().getTargetHumidity() > this.humidity) {
                        airVentilationHumidityModifier = this.carriage.getAirVentilation().getDehumidifierModifier()
                                * (this.carriage.getAirVentilation().getDehumidifierEfficiency())/100.0;
                    } else if (this.carriage.getAirVentilation().getTargetHumidity() < this.humidity) {
                        airVentilationHumidityModifier = this.carriage.getAirVentilation().getDehumidifierModifier()
                                * (this.carriage.getAirVentilation().getDehumidifierEfficiency())/100.0 * (-1);
                    }
                } else {
                    if (this.defaults.EXTERIORS_HUMIDITY > this.humidity) {
                        airVentilationHumidityModifier = this.defaults.DOOR_OPEN_HUMIDITY_MODIFIER;
                    } else if (this.defaults.EXTERIORS_HUMIDITY < this.humidity) {
                        airVentilationHumidityModifier = this.defaults.DOOR_OPEN_HUMIDITY_MODIFIER * (-1);
                    }
                }
            }

            this.temperature = this.temperature + airVentilationTemperatureModifier + doorsOpenTemperatureModifier;
            this.humidity = this.humidity + airVentilationHumidityModifier + doorsOpenHumidityModifier;

            Main.TEMPERATURE_SIM_LOGGER.info("Doors open: " + numDoorsOpen +
                    "\nDoor open temp modifier: " + doorsOpenTemperatureModifier +
                    "\nDoor open humidity modifier: " + doorsOpenHumidityModifier +
                    "\nAre air vents open? " + this.carriage.getAirVentilation().areAirVentsOpen() +
                    "\nIs air ventilation on? " + this.carriage.getAirVentilation().isAirVentilationOn() +
                    "\nIs dehumidifier on? " + this.carriage.getAirVentilation().isDehumidifierOn() +
                    "\nAir ventilation modifier: " + airVentilationTemperatureModifier +
                    "\nDehumidifier modifier:" + airVentilationHumidityModifier +
                    "\nExteriors temperature: " + this.defaults.EXTERIORS_TEMPERATURE +
                    "\nExteriors humidity: " + this.defaults.EXTERIORS_HUMIDITY +
                    "\nTarget temperature: " + this.carriage.getAirVentilation().getTargetAirTemperature() +
                    "\nTarget humidity: " + this.carriage.getAirVentilation().getTargetHumidity() +
                    "\nNew temperature: " + this.temperature +
                    "\nNew humidity: " + this.humidity);

            if(this.carriage.getAirVentilation().getBatteryCharge() > 0.0) {
                this.buffers.getTemperatureBufferWriter().write(this.temperature);
                this.buffers.getHumidityBufferWriter().write(this.humidity);
            }
        }, 0, 60/super.updateFrequency, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }

    @Override
    public String toString() {
        return "TemperatureHumiditySimulationModule{" +
                "temperature=" + temperature +
                ", scheduler=" + scheduler +
                ", defaults=" + defaults +
                ", simulationRate=" + updateFrequency +
                '}';
    }
}
