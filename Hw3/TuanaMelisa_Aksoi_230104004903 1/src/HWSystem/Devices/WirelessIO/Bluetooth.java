package src.HWSystem.Devices.WirelessIO;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

/**
 * Implementation of a Bluetooth wireless communication device.
 * This class provides functionality for wireless communication
 * using Bluetooth protocol.
 */
public class Bluetooth extends WirelessIO{

    /**
     * Constructs a new Bluetooth device with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this Bluetooth device
     */
    public Bluetooth(Protocol protocol){
        super(protocol); 
    }

    /**
     * Turns the Bluetooth device on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON.\n", getName()); 

        getProtocol().write("turnON"); 
    }

    /**
     * Turns the Bluetooth device off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff(){
        state = State.OFF; 

        System.out.printf("%s: Turning OFF.\n", getName()); 

        getProtocol().write("turnOFF"); 
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "Bluetooth" representing this device
     */
    @Override
    public String getName(){
        return "Bluetooth"; 
    }

    /**
     * Sends data through the Bluetooth interface.
     * Outputs the data to the console and sends it through the protocol.
     *
     * @param data The data to be sent
     */
    @Override
    public void sendData(String data){
        System.out.printf("%s: Sending \"%s\".\n", getName(), data);

        getProtocol().write(data); 
    }

    /**
     * Receives data from the Bluetooth interface.
     * Reads data from the protocol and outputs it to the console.
     *
     * @return The received data as a string
     */
    @Override
    public String recvData(){
        String received = getProtocol().read();

        System.out.printf("%s: Received \"%s\".\n", getName(), received);
        
        return received;
    }


}
