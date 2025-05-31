package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Protocols.Protocol;

import src.HWSystem.Devices.State;

/**
 * Implementation of a GY951 IMU sensor.
 * This class provides functionality for reading acceleration and rotation
 * using the GY951 sensor.
 */
public  class GY951 extends IMUSensor{
    /**
     * Constructs a new GY951 sensor with the protocol.
     *
     * @param protocol The communication protocol to be used by this sensor
     */
    public GY951(Protocol protocol){
        super(protocol);
        
    }

    /**
     * Turns the GY951 sensor on.
     * Sets the device state to ON and sends a turnON command through the protocol.
     */
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    /**
     * Turns the GY951 sensor off.
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
     * @return The string "GY951" representing this device
     */
    @Override
    public String getName(){
        return "GY951";
    }

    /**
     * Gets the current acceleration reading from the GY951 sensor.
     * Returns a fixed acceleration value.
     *
     * @return The acceleration value
     */
    @Override
    public float getAccel(){
        
        return 1.00f;
    }

    /**
     * Gets the current rotation reading from the GY951 sensor.
     * Returns a fixed rotation value.
     *
     * @return The rotation value
     */
    @Override
    public float getRot(){
        
        return 0.50f;
    }
}
