package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface IAirVentilation {
    boolean areAirVentsOpen();
    void openAirVents();
    void closeAirVents();
    boolean isAirVentilationOn();
    void turnAirVentilationOn();
    void turnAirVentilationOff();
    boolean isDehumidifierOn();
    void turnDehumidifierOn();
    void turnDehumidifierOff();
    double getAirVentilationModifier();
    void setAirVentilationModifier(double modifier);
    double getDehumidifierModifier();
    void setDehumidifierModifier(double modifier);
    double getAirVentilationEfficiency();
    double getDehumidifierEfficiency();
    double getTargetAirTemperature();
    void setTargetAirTemperature(double targetAirTemperature);
    double getTargetHumidity();
    void setTargetHumidity(double targetHumidity);
}
