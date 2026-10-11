package it.unimore.fum.iot.utils.types.simulation.carriage;

import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;

public interface ILamp {
    boolean isOn();
    void turnOn();
    void turnOff();
    BrightnessLevelsEnum getBrightness();
    void setBrightness(BrightnessLevelsEnum brightness);
}
