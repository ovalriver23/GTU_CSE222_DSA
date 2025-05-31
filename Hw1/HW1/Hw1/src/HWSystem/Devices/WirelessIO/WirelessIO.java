package src.HWSystem.Devices.WirelessIO;

import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

// Abstract class representing a wireless I/O device, which extends from the Device class
public abstract class WirelessIO extends Device{
    // Constructor to initialize the wireless I/O device with a given protocol
    public WirelessIO(Protocol protocol){
        super(protocol);
    }

    @Override 
    public String getDevType(){
        return "WirelessIO"; // Return the device type as a string
    }

    //Abstract methods to send and receive data
    public abstract void sendData(String data);
    public abstract String recvData();


}
