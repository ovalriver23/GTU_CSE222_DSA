package src.HWSystem.Devices.Display;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

public class LCD extends Display{
    //Initializes the LCD display with the specific protocol
    public LCD(Protocol protocol){
        super(protocol); // Call the constructor of the superclass Display
    }

    // Turn the LCD display ON
    @Override
    public void turnOn(){
        state = State.ON; // Set the state of the LCD to ON

        // Print a message indicating the LCD is turning on
        System.out.printf("%s: Turning ON\n", getName());

        // Send the "turnON" command via the protocol to the LCD
        getProtocol().write("turnON");
    }

    // Turn the LCD display OFF
    @Override
    public void turnOff(){
        state = State.OFF; // Set the state of the LCD to OFF

        System.out.printf("%s: Turnign Off\n", getName());

        // Send the "turnOFF" command via the protocol to the LCD
        getProtocol().write("turnOFF");
    }


    @Override
    public String getName(){
        return "LCD";  // Return the name of the device as a string
    }


    // Print data on the LCD display
    @Override
    public void printData(String data){

        // Print the data to the console
        System.out.printf("%s: Printing Data - %s\n", getName(), data);

        // Send the data to the protocol
        getProtocol().write(getName() + " Displaying: " + data);

    }
}
