package src.HWSystem.Devices.MotorDriver;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

/**
 * Implementation of a SparkFun motor driver device.
 * This class provides functionality for controlling motors
 * using the SparkFun motor driver.
 */
public class SparkFunMD extends MotorDriver{

    /**
     * Constructs a new SparkFun motor driver with the specified protocol.
     *
     * @param protocol The communication protocol to be used by this motor driver
     */
    public SparkFunMD(Protocol protocol){
        super(protocol);
    }

    /**
     * Turns the motor driver on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON.\n", getName());

        getProtocol().write("turnON"); 
    }

    /**
     * Turns the motor driver off.
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
     * @return The string "SparkFunMD" representing this device
     */
    @Override
    public String getName(){
        return "SparkFunMD";
    }

    /**
     * Sets the speed of the motor.
     * Validates the speed value and sends it through the protocol.
     *
     * @param speed The speed value to set for the motor (0-100)
     * @throws IllegalArgumentException if the speed is not between 0 and 100
     */
    @Override
    public void setMotorSpeed(int speed){
        if(speed < 0 || speed > 100){
            throw new IllegalArgumentException("Motor speed must be between 0 and 100");
        }
         
        System.out.printf("%s: Setting speed to %d.\n", getName(), speed);
    
        getProtocol().write(String.valueOf(speed));
    }
}
