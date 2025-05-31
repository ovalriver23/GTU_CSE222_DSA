package src.HWSystem.Devices.Sensor.TempSensor;
import src.HWSystem.Devices.State;
import src.HWSystem.Protocols.Protocol;

/**
 * Implementation of a BME280 temperature sensor.
 * This class provides functionality for reading temperature
 * using the BME280 sensor.
 */
public class BME280 extends TempSensor {
    /**
     * Constructs a new BME280 sensor with the protocol.
     *
     * @param protocol The communication protocol to be used by this sensor
     */
    public BME280(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns the BME280 sensor on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn() {
        state = State.ON;
        // Prints a message and sends the "turnON" command to the protocol.
        System.out.printf("%s: Turning ON\n", getName());
        getProtocol().write("turnON");
    }

    /**
     * Turns the BME280 sensor off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff() {
        state = State.OFF;
        // Prints a message and sends the "turnOFF" command to the protocol.
        System.out.printf("%s: Turning OFF\n", getName());
        getProtocol().write("turnOFF");
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "BME280" representing this device
     */
    @Override
    public String getName() {
        return "BME280";
    }

    /**
     * Gets the current temperature reading from the BME280 sensor.
     * Simulates reading from the sensor and returns a fixed temperature value.
     *
     * @return The temperature in degrees Celsius
     */
    @Override
    public float getTemp() {
        getProtocol().read(); 
        return 24.00f;
    }
}
