package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

// The MPU6050 class represents a specific IMU sensor
public class MPU6050 extends IMUSensor{
    // Constructor initializes the MPU6050 sensor with a protocol.
    public MPU6050(Protocol protocol){
        super(protocol);
    }

    // Turns on the MPU6050 sensor
    @Override
    public void turnOn(){
        state = State.ON; // Set sensor state to ON

        System.out.printf("%s: Turning ON\n", getName()); // Output message to indicate the sensor is turning on

        getProtocol().write("turnON"); // Send the "turnON" command via the protocol
    }

    // Turns off the MPU6050 sensor 
    @Override
    public void turnOff(){
        state = State.OFF; // Set sensor state to OFF

        System.out.printf("%s: Turnign Off\n", getName()); // Output message to indicate the sensor is turning off

        getProtocol().write("turnOFF"); // Send the "turnOFF" command via the protocol
    }


    @Override
    public String getName(){
        return "MPU6050"; // Returns the name of the sensor ("MPU6050").
    }

    @Override
    public float getAccel(){
        getProtocol().read(); //reading data from the protocol
        return (float)Math.random() * 100.0f; // Return a random acceleration
    }

    public float getRot(){
        getProtocol().read(); //reading data from the protocol
        return (float)Math.random() * 100.0f; // Return a random rotation
    }

}
