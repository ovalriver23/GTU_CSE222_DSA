package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Devices.Sensor.Sensor;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for IMU sensors.
 * Extends the Sensor class to provide IMU functionality.
 */
public abstract class IMUSensor extends Sensor{
    /**
     * Constructs a new IMUSensor with the protocol.
     *
     * @param protocol The communication protocol to be used by this IMU sensor
     */
    public IMUSensor(Protocol protocol){
        super(protocol);
    }

    /**
     * Returns the specific type of the sensor.
     *
     * @return The string "IMUSensor" representing this sensor type
     */
    @Override 
    public String getSensType(){
        return "IMUSensor";
    }

    /**
     * Gets the acceleration data from the IMU sensor.
     * Implementation must be provided by concrete IMU sensor classes.
     *
     * @return The acceleration value
     */
    public abstract float getAccel();

    /**
     * Gets the rotational data from the IMU sensor.
     * Implementation must be provided by concrete IMU sensor classes.
     *
     * @return The rotation value
     */
    public abstract float getRot();

    /**
     * Converts the IMU data into a formatted string.
     *
     * @return A string representation of the acceleration and rotation data
     */
    @Override
    public String data2String() {
        return String.format("Acceleration: %.2f, Rotation: %.2f", getAccel(), getRot());
    }
}
