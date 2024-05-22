package com.innovativesolutions.iotcontroller.Models;

public class DoorMotor {
    boolean isOpen;

    public DoorMotor() {
    }

    public DoorMotor(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
