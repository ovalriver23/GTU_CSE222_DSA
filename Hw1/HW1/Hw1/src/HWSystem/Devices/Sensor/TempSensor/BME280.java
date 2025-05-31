package src.HWSystem.Devices.Sensor.TempSensor;
import src.HWSystem.Devices.State;
import src.HWSystem.Protocols.Protocol;


// The BME280 class extends TempSensor
public class BME280 extends TempSensor{
    
    // Constructor that initializes the BME280 sensor with a protocol for communication.
    public BME280(Protocol protocol){
        super(protocol);
    }


    // Turns the BME280 sensor on and sets its state to ON.
    @Override
    public void turnOn(){
        state = State.ON;
        // Prints a message and sends the "turnON" command to the protocol.
        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    // Turns the BME280 sensor off and sets its state to OFF.
    @Override
    public void turnOff(){
        state = State.OFF;

        // Prints a message and sends the "turnOFF" command to the protocol.
        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF");
    }


    @Override
    public String getName() {
        return "BME280";// Returns the name of the sensor
    }

    @Override
    public float getTemp(){
        getProtocol().read(); //read action from the sensor.

        // Returns a random temperature value between 0 and 100.
        return (float)(Math.random() * 100.0f);
    }

}
