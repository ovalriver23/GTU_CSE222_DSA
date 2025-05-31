package src.HWSystem.Devices.Sensor.TempSensor;
import src.HWSystem.Devices.State;
import src.HWSystem.Protocols.Protocol;


// The DHT11 class extends TempSensor 
public class DHT11 extends TempSensor{
    
    // Constructor that initializes the DHT11 sensor with a protocol.
    public DHT11(Protocol protocol){
        super(protocol);
    }


    // Turns the DHT11 sensor on and changes its state to ON.
    @Override
    public void turnOn(){
        state = State.ON;
        // Prints a message and sends the "turnON" command to the protocol.
        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    // Turns the DHT11 sensor off and changes its state to OFF.
    @Override
    public void turnOff(){
        state = State.OFF;

        // Prints a message and sends the "turnOFF" command to the protocol.
        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF");
    }

    //Returns the name of the sensor, which is "DHT11".
    @Override
    public String getName(){
        return "DHT11";
    }

    @Override
    public float getTemp(){
        getProtocol().read(); // Simulates a read action from the sensor.

        // Returns a random temperature value between 0 and 100.
        return (float)(Math.random() * 100.0);
    }
}
