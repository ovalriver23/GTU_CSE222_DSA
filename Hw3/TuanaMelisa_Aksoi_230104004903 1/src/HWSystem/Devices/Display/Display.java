package src.HWSystem.Devices.Display;

import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for display devices in the system.
 */
public abstract class Display extends Device {
    /**
     * Constructs a new Display device with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this display
     */
    public Display(Protocol protocol) {
        super(protocol); 
    }

    /**
     * Prints data to the display.
     * Implementation must be provided by concrete display classes.
     *
     * @param data The data to be displayed
     */
    public abstract void printData(String data);

    /**
     * Returns the type of the device.
     *
     * @return The string "Display" representing this device type
     */
    @Override
    public String getDevType(){
        return "Display"; 
    }
}
