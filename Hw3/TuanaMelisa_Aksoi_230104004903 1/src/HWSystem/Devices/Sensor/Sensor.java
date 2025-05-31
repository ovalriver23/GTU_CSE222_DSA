package src.HWSystem.Devices.Sensor;

import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for sensor devices in the system.
 * Extends the Device class to provide sensor-specific functionality.
 */
public abstract class Sensor extends Device{
    /**
     * Constructs a new Sensor with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this sensor
     */
    public Sensor(Protocol protocol){
        super(protocol);
    }

    /**
     * Returns the type of the device.
     * Combines the sensor type with "Sensor" to form the complete device type.
     *
     * @return The device type as a string
     */
    @Override
    public String getDevType(){
        return getSensType() + " Sensor";
    }

    /**
     * Returns the specific type of the sensor.
     * Implementation must be provided by concrete sensor classes.
     *
     * @return The sensor type as a string
     */
    public abstract String getSensType();
    
    /**
     * Converts the sensor data into a string representation.
     * Implementation must be provided by concrete sensor classes.
     *
     * @return The sensor data as a string
     */
    public abstract String data2String(); 
}
