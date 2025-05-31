package src.HWSystem.Devices.Sensor.TempSensor;
import src.HWSystem.Devices.State;
import src.HWSystem.Protocols.Protocol;

/**
 * Implementation of a DHT11 temperature sensor.
 * This class provides functionality for reading temperature
 * using the DHT11 sensor.
 */
public class DHT11 extends TempSensor{
    
    /**
     * Constructs a new DHT11 sensor with the protocol.
     *
     * @param protocol The communication protocol to be used by this sensor
     */
    public DHT11(Protocol protocol){
        super(protocol);
    }

    /**
     * Turns the DHT11 sensor on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn(){
        state = State.ON;
        // Prints a message and sends the "turnON" command to the protocol.
        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    /**
     * Turns the DHT11 sensor off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff(){
        state = State.OFF;

        // Prints a message and sends the "turnOFF" command to the protocol.
        System.out.printf("%s: Turning OFF\n", getName());

        getProtocol().write("turnOFF");
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "DHT11" representing this device
     */
    @Override
    public String getName(){
        return "DHT11";
    }

    /**
     * Gets the current temperature reading from the DHT11 sensor.
     * Simulates reading from the sensor and returns a fixed temperature value.
     *
     * @return The temperature
     */
    @Override
    public float getTemp(){
        getProtocol().read(); 

        return 24.00f;
    }
}
