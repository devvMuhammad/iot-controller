package com.innovativesolutions.iotcontroller.Models;

public class TemperatureSensor {
    double temperature;
    Boolean isOn;

    public TemperatureSensor() {
    }

    public TemperatureSensor(double temperature, Boolean isOn) {
        this.temperature = temperature;
        this.isOn = isOn;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Boolean getOn() {
        return isOn;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }
}
