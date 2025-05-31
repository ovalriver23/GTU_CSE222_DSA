package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Devices.Sensor.Sensor;
import src.HWSystem.Protocols.Protocol;

// Abstract class representing an IMU Sensor
public abstract class IMUSensor extends Sensor{
    // Constructor that initializes the IMU sensor with a protocol.
    public IMUSensor(Protocol protocol){
        super(protocol);
    }

    // Overrides the getSensType method to return the type of sensor
    @Override 
    public String getSensType(){
        return "IMUSensor";
    }

    // Abstract method to get the acceleration data from the IMU sensor.
    public abstract float getAccel();

    // Abstract method to get the rotational data from the IMU sensor.
    public abstract float getRot();

    // Converts the acceleration and rotation data to a string format
    @Override
    public String data2String() {
        return String.format("Accel: %.2f, Rot: %.2f", getAccel(), getRot());
    }
}
