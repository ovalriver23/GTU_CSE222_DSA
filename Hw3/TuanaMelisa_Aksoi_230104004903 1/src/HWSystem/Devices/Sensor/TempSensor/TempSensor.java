package src.HWSystem.Devices.Sensor.TempSensor;

import src.HWSystem.Devices.Sensor.Sensor;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for temperature sensor devices.
 * Extends the Sensor class to provide temperature functionality.
 */
public abstract class TempSensor extends Sensor {
    /**
     * Constructs a new TempSensor with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this temperature sensor
     */
    public TempSensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Gets the current temperature reading from the sensor.
     * Implementation must be provided by concrete temperature sensor classes.
     *
     * @return The temperature in degrees Celsius
     */
    public abstract float getTemp();

    /**
     * Returns the specific type of the sensor.
     *
     * @return The string "TempSensor" representing this sensor type
     */
    @Override 
    public String getSensType() {
        return "TempSensor";
    }

    /**
     * Converts the temperature data into a formatted string.
     *
     * @return A string representation of the temperature 
     */
    @Override
    public String data2String() {
        return String.format("Temperature: %.2fC", getTemp());
    }
}
