package src.HWSystem.Devices.Display;

import src.HWSystem.Devices.Device;

import src.HWSystem.Protocols.Protocol;

public abstract class Display extends Device{
    public Display(Protocol protocol){
        super(protocol); // Call the constructor of the superclass Device
    }

    // Abstract method that subclasses must implement to print data to the display
    public abstract void printData(String data);

    @Override
    public String getDevType(){
        return "Display"; // Return the device type as a string
    }


}
