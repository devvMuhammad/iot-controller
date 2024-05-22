package com.innovativesolutions.iotcontroller.Models;

public class Fan {
    int speed;
    boolean isOn;

    public Fan() {
    }
    public Fan(int speed, boolean isOn) {
        this.speed = speed;
        this.isOn = isOn;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
