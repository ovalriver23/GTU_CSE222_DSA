package src.HWSystem.Devices;

import src.HWSystem.Protocols.Protocol;

public abstract class Device {
    private Protocol protocol; // The protocol used by this device for communication
    protected State state; // The current state of the device (ON or OFF)

    // Constructor to initialize the device with a given protocol
    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF; // Set the initial state of the device to OFF
    }

    // Getter for the protocol used by the device
    public Protocol getProtocol() {
        return protocol;
    }

    // Getter for the current state of the device
    public State getState() {
        return state;
    }

    //Methods to turnOn, turnOff, get the name of the device, and get the type fo the device
    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getName();
    public abstract String getDevType();
}