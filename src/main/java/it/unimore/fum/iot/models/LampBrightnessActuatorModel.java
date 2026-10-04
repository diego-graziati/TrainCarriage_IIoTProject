package it.unimore.fum.iot.models;

import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;

public class LampBrightnessActuatorModel {
    private long timestamp;
    private BrightnessLevelsEnum brightnessLevel;
    private double lowBrightness;
    private double mediumBrightness;
    private double highBrightness;

    private String lampBrightnessUnit;

    private final ActuatorDriver<BrightnessLevelsEnum> brightnessActuator;

    public LampBrightnessActuatorModel(ActuatorDriver<BrightnessLevelsEnum> brightnessActuator) {
        this.timestamp = System.currentTimeMillis();
        this.brightnessLevel = BrightnessLevelsEnum.LOW;
        this.lowBrightness = 10.0;
        this.mediumBrightness = 20.0;
        this.highBrightness = 30.0;
        this.lampBrightnessUnit = "cd/m2";

        this.brightnessActuator = brightnessActuator;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public BrightnessLevelsEnum getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(BrightnessLevelsEnum brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
        this.brightnessActuator.execute(this.brightnessLevel);
    }

    public double getLowBrightness() {
        return lowBrightness;
    }

    public void setLowBrightness(double lowBrightness) {
        this.lowBrightness = lowBrightness;
    }

    public double getMediumBrightness() {
        return mediumBrightness;
    }

    public void setMediumBrightness(double mediumBrightness) {
        this.mediumBrightness = mediumBrightness;
    }

    public double getHighBrightness() {
        return highBrightness;
    }

    public void setHighBrightness(double highBrightness) {
        this.highBrightness = highBrightness;
    }

    public String getLampBrightnessUnit() {
        return lampBrightnessUnit;
    }

    public void setLampBrightnessUnit(String lampBrightnessUnit) {
        this.lampBrightnessUnit = lampBrightnessUnit;
    }

    @Override
    public String toString() {
        return "LampBrightnessActuatorModel{" +
                "timestamp=" + timestamp +
                ", brightnessLevel=" + brightnessLevel + " " + lampBrightnessUnit +
                ", lowBrightness=" + lowBrightness + " " + lampBrightnessUnit +
                ", mediumBrightness=" + mediumBrightness + " " + lampBrightnessUnit +
                ", highBrightness=" + highBrightness + " " + lampBrightnessUnit +
                '}';
    }
}
