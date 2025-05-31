package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

/**
 * Implementation of an MPU6050 IMU sensor.
 * This class provides functionality for reading acceleration and rotation
 * using the MPU6050 sensor.
 */
public class MPU6050 extends IMUSensor{
    /**
     * Constructs a new MPU6050 sensor with the protocol.
     *
     * @param protocol The communication protocol to be used by this sensor
     */
    public MPU6050(Protocol protocol){
        super(protocol);
    }

    /**
     * Turns the MPU6050 sensor on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn(){
        state = State.ON; 

        System.out.printf("%s: Turning ON\n", getName()); 

        getProtocol().write("turnON");
    }

    /**
     * Turns the MPU6050 sensor off.
     * Sets the device state to OFF and sends a turnOFF command through the protocol.
     */
    @Override
    public void turnOff(){
        state = State.OFF; 

        System.out.printf("%s: Turning OFF\n", getName()); 

        getProtocol().write("turnOFF"); 
    }

    /**
     * Returns the name of this device.
     *
     * @return The string "MPU6050" representing this device
     */
    @Override
    public String getName(){
        return "MPU6050";
    }

    /**
     * Gets the current acceleration reading from the MPU6050 sensor.
     * Simulates reading from the sensor and returns a fixed acceleration value.
     *
     * @return The acceleration value
     */
    @Override
    public float getAccel(){
        
        return 1.00f;
    }

    /**
     * Gets the current rotation reading from the MPU6050 sensor.
     * Simulates reading from the sensor and returns a fixed rotation value.
     *
     * @return The rotation value
     */
    @Override
    public float getRot(){
        
        return 0.50f;
    }

}
