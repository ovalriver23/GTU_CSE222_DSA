package src.HWSystem.Devices.MotorDriver;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

public class SparkFunMD extends MotorDriver{

    // Constructor to initialize the motor driver with the given protocol.
    public SparkFunMD(Protocol protocol){
        super(protocol);
    }

    // Turns the motor on and updates the motor's state to ON.
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON"); // Sends the turnON command via protocol.
    }

    // Turns the motor off and updates the motor's state to OFF.
    @Override
    public void turnOff(){
        state = State.OFF;

        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF"); // Sends the turnOFF command via protocol.
    }

    // Returns the name of the motor driver (SparkFunMD).
    @Override
    public String getName(){
        return "SparkFunMD";
    }

     // Sets the motor speed
    public void setMotorSpeed(int speed){
         // Ensures the speed is within the valid range.
        if(speed < 0 || speed > 100){
            throw new IllegalArgumentException("Motor speed must be between 0 and 100");
        }
             
        System.out.printf("%s: Setting motor speed to %d%%\n", getName(), speed);
        
        // Sends the speed command to the protocol.
        String command = String.format("SPEED:%d", speed);
        getProtocol().write(command);
    
    }
}
