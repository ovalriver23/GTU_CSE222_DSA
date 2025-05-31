package src.HWSystem.Devices.WirelessIO;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

public class Bluetooth extends WirelessIO{

    public Bluetooth(Protocol protocol){
        super(protocol); // Call the constructor of the superclass WirelessIO
    }

    @Override
    public void turnOn(){
        state = State.ON; // Set the device's state to ON

        System.out.printf("%s: Turning ON\n", getName()); // Print a message indicating the device is turning on

        getProtocol().write("turnON"); // Use the protocol to send the "turnON" command
    }

    @Override
    public void turnOff(){
        state = State.OFF; // Set the device's state to OFF

        System.out.printf("%s: Turnign Off\n", getName()); // Print a message indicating the device is turning off

        getProtocol().write("turnOFF"); // Use the protocol to send the "turnOFF" command
    }

    @Override
    public String getName(){
        return "Bluetooth"; // Return the device name as a string
    }

    // Override the sendData method to send data using the protocol
    public void sendData(String data){
        System.out.printf("%s: Sending data.\n", getName(), data);

        getProtocol().write(data); // Use the protocol to send the data
    }

    // Override the recvData method to receive data using the protocol
    public String recvData(){
        String received = getProtocol().read();

        System.out.printf("%s: Receiving data.\n%s\n", getName(), received);

        // Return the received data
        return received;
    }


}
