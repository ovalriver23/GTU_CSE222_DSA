package src.HWSystem.Devices.Display;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

/**
 * This class provides functionality for OLED displays including
 * turning on/off and displaying data.
 */
public class OLED extends Display {
    /**
     * Constructs a new OLED display with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this OLED
     */
    public OLED(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns the OLED display on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn() {
        state = State.ON;
        System.out.printf("%s: Turning ON.\n", getName());
        getProtocol().write("turnON");
    }

    /**
     * Turns the OLED display off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff() {
        state = State.OFF;
        System.out.printf("%s: Turning OFF.\n", getName());
        getProtocol().write("turnOFF");
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "OLED" representing this device
     */
    @Override
    public String getName() {
        return "OLED";
    }

    /**
     * Prints data on the OLED display.
     * Outputs the data to both the console and sends it through the protocol.
     *
     * @param data The data to be displayed on the OLED
     */
    @Override
    public void printData(String data) {
        System.out.printf("%s: Printing \"%s\".\n", getName(), data);
        getProtocol().write(data);
    }
}
