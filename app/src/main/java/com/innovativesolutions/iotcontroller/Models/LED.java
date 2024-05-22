package com.innovativesolutions.iotcontroller.Models;

public class LED {
    int brightness;
    String color;
    boolean isOn;

    public LED() {
    }

    public LED(int brightness,  boolean isOn) {
        this.brightness = brightness;
        this.color = "Blue";
        this.isOn = isOn;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
