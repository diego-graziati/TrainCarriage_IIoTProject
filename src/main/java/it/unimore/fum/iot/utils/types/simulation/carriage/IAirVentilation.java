package it.unimore.fum.iot.utils.types.simulation.carriage;

public interface IAirVentilation {
    public boolean areAirVentsOpen();
    public void openAirVents();
    public void closeAirVents();
    public boolean isAirVentilationOn();
    public void turnAirVentilationOn();
    public void turnAirVentilationOff();
    public boolean isDehumidifierOn();
    public void turnDehumidifierOn();
    public void turnDehumidifierOff();
    public double getAirVentilationModifier();
    public void setAirVentilationModifier(double modifier);
    public double getDehumidifierModifier();
    public void setDehumidifierModifier(double modifier);
    public double getAirVentilationEfficiency();
    public double getDehumidifierEfficiency();
    public double getTargetAirTemperature();
    public void setTargetAirTemperature(double targetAirTemperature);
    public double getTargetHumidity();
    public void setTargetHumidity(double targetHumidity);
}
