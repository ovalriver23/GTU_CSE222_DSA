package src.HWSystem.Devices.Sensor.TempSensor;

import src.HWSystem.Devices.Sensor.Sensor;
import src.HWSystem.Protocols.Protocol;



// The TempSensor class is an abstract class that extends Sensor
public abstract class TempSensor extends Sensor{
    // Constructor initializes the TempSensor with a protocol.
    public TempSensor(Protocol protocol){
        super(protocol);
    }

    // Abstract method to get the temperature from the sensor.
    public abstract float getTemp();

    @Override 
    public String getSensType(){
        return "TempSensor";  // Returns the sensor type
    }

    // Converts the temperature data into a string format
    @Override
    public String data2String() {
        return String.format("Temp: %.2f°C", getTemp());
    }
}
