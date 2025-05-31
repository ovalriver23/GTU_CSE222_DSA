package src.HWSystem.Devices.WirelessIO;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

public class Wifi extends WirelessIO{
    public Wifi (Protocol protocol){
        super(protocol); // Call the constructor of the superclass WirelessIO
    }

    // Override the turnOn method to set the state to ON and send a "turnON" command using the protocol
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    // Override the turnOff method to set the state to OFF and send a "turnOFF" command using the protocol
    @Override
    public void turnOff(){
        state = State.OFF;

        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF");
    }


    @Override
    public String getName(){
        return "WIFI"; // Return the device name as a string
    }

    // Override the sendData method to send data using the protocol
    public void sendData(String data){
        System.out.printf("%s: Sending \"\n", getName(), data);

        // Use the protocol to send the data
        getProtocol().write(data);
    }

    // Override the recvData method to receive data using the protocol
    public String recvData(){
        String received = getProtocol().read();

        // Print a message indicating the data is being received
        System.out.printf("%s: Receiving data.\n%s\n", getName(), received);

        // Return the received data
        return received;
    }
}
