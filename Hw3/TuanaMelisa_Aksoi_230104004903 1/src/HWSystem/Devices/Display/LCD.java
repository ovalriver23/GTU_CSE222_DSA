package src.HWSystem.Devices.Display;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

/**
 * This class provides functionality for LCD displays including
 * turning on/off and displaying data.
 */
public class LCD extends Display {
    /**
     * Constructs a new LCD display with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this LCD
     */
    public LCD(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns the LCD display on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn() {
        state = State.ON;
        System.out.printf("%s: Turning ON\n", getName());
        getProtocol().write("turnON");
    }

    /**
     * Turns the LCD display off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff() {
        state = State.OFF;
        System.out.printf("%s: Turning OFF\n", getName());
        getProtocol().write("turnOFF");
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "LCD" representing this device
     */
    @Override
    public String getName() {
        return "LCD";
    }

    /**
     * Prints data on the LCD display.
     * Outputs the data to both the console and sends it through the protocol.
     *
     * @param data The data to be displayed on the LCD
     */
    @Override
    public void printData(String data) {
        System.out.printf("%s: Printing \"%s\"\n", getName(), data);
        getProtocol().write(data);
    }
}
