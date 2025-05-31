package src.HWSystem.Devices.MotorDriver;
import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

/**
 * Abstract base class for motor driver devices in the system.
 * Extends the Device class to provide motor control functionality.
 */
public abstract class MotorDriver extends Device{
    /**
     * Constructs a new MotorDriver with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this motor driver
     */
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the type of the device.
     *
     * @return The string "MotorDriver" representing this device type
     */
    @Override
    public String getDevType()
    {
        return "MotorDriver"; 
    }

    /**
     * Sets the speed of the motor.
     * Implementation must be provided by concrete motor driver classes.
     *
     * @param speed The speed value to set for the motor
     */
    public abstract void setMotorSpeed(int speed);
}
