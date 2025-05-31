package src.HWSystem.Devices;

import src.HWSystem.Protocols.Protocol;

/**
 * This class provides basic functionality for device management including
 * state control and protocol communication.
 */
public abstract class Device {
    /** The protocol used by this device for communication */
    private Protocol protocol;
    
    /** The current state of the device (ON or OFF) */
    protected State state;

    /**
     * Constructs a new Device with the specified protocol.
     * Initializes the device in the OFF state.
     *
     * @param protocol The communication protocol to be used by this device
     */
    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF;
    }

    /**
     * Returns the protocol used by this device.
     *
     * @return The communication protocol associated with this device
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * Returns the current state of the device.
     *
     * @return The current state (ON or OFF) of the device
     */
    public State getState() {
        return state;
    }

    /**
     * Turns the device on.
     */
    public abstract void turnOn();

    /**
     * Turns the device off.
     */
    public abstract void turnOff();

    /**
     * Returns the name of the device.
     *
     * @return The name of the device
     */
    public abstract String getName();

    /**
     * Returns the type of the device.
     *
     * @return The type of the device
     */
    public abstract String getDevType();
}