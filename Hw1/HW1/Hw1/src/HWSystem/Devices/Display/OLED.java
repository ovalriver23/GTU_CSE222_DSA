package src.HWSystem.Devices.Display;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;



// OLED class extends the abstract Display class
public class OLED extends Display{
    // It calls the parent constructor (Display) to set up the protocol.
    public OLED(Protocol protocol){
        super(protocol);
    }

    @Override
    public void turnOn(){
        state = State.ON; // Set the device's state to ON

        // Print a message indicating that the OLED is turning ON
        System.out.printf("%s: Turning ON\n", getName());

        // Send the "turnON" command via the protocol to the LCD
        getProtocol().write("turnON");
    }

    @Override
    public void turnOff(){
        state = State.OFF; // Set the device's state to OFF

        //Print that OLED is turning OFF
        System.out.printf("%s: Turnign Off\n", getName());

        // Send the "turnOFF" command via the protocol to the OLED
        getProtocol().write("turnOFF");
    }


    @Override
    public String getName(){
        return "OLED"; // Return the name of the device as a string
    }

    //print the provided data on the OLED display.
    @Override
    public void printData(String data){
        // Print the data to the console
        System.out.printf("%s: Printing Data - %s\n", getName(), data);
        
        // Send the data to the protocol
        getProtocol().write(getName() + " Displaying: " + data);

    }
    
}
