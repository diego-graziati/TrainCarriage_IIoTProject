package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;

public interface ILamp {
    public boolean isOn();
    public void turnOn();
    public void turnOff();
    public BrightnessLevelsEnum getBrightness();
    public void setBrightness(BrightnessLevelsEnum brightness);
}
