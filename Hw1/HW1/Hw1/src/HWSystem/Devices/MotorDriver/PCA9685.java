package src.HWSystem.Devices.MotorDriver;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Devices.State;

public class PCA9685 extends MotorDriver {

    // Initializes the PCA9685 motor driver with the given protocol.
    public PCA9685(Protocol protocol){
        super(protocol);
    }


    // Turns the motor on and updates its state.
    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON"); // Sends turnON command via protocol
    }

    // Turns the motor off and updates its state.
    @Override
    public void turnOff(){
        state = State.OFF;

        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF"); // Sends turnOFF command via protocol
    }


    // Returns the name of the device (PCA9685).
    @Override
    public String getName(){
        return "PCA9685";
    }

    // Sets the motor speed
    @Override
    public void setMotorSpeed(int speed){
        if(speed < 0 || speed > 100){
            throw new IllegalArgumentException("Motor speed must be between 0 and 100");
        }
         
        System.out.printf("%s: Setting motor speed to %d%%\n", getName(), speed);
        
        // Sends the speed command to the protocol.
        String command = String.format("SPEED:%d", speed);
        getProtocol().write(command);
    }
}
