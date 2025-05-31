package src.HWSystem.Devices.Sensor;

import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

// Abstract class representing a Sensor device
public abstract class Sensor extends Device{
    // Constructor that initializes the sensor with a protocol.
    public Sensor(Protocol protocol){
        super(protocol);
    }

    // Returns the device type as a string, including the sensor type 
    @Override
    public String getDevType(){
        return getSensType() + " Sensor";
    }

    // Abstract method to get the specific type of the sensor 
    public abstract String getSensType();
    
    // Abstract method to convert the sensor data into a string representation.
    public abstract String data2String(); 
}
