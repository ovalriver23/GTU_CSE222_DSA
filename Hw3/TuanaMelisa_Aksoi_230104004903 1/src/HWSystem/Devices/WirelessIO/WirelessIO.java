package src.HWSystem.Devices.WirelessIO;

import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for wireless I/O devices in the system.
 * Extends the Device class to provide wireless communication functionality.
 */
public abstract class WirelessIO extends Device{
    /**
     * Constructs a new WirelessIO device with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this wireless device
     */
    public WirelessIO(Protocol protocol){
        super(protocol);
    }

    /**
     * Returns the type of the device.
     *
     * @return The string "WirelessIO" representing this device type
     */
    @Override 
    public String getDevType(){
        return "WirelessIO"; 
    }

    /**
     * Sends data through the wireless interface.
     * Implementation must be provided by concrete wireless device classes.
     *
     * @param data The data to be sent
     */
    public abstract void sendData(String data);

    /**
     * Receives data from the wireless interface.
     * Implementation must be provided by concrete wireless device classes.
     *
     * @return The received data as a string
     */
    public abstract String recvData();


}
