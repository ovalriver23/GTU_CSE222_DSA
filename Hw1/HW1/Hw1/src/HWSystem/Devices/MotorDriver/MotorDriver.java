package src.HWSystem.Devices.MotorDriver;
import src.HWSystem.Devices.Device;
import src.HWSystem.Protocols.Protocol;

// MotorDriver is an abstract class that extends the Device
public abstract class MotorDriver extends Device{
    // It calls the parent constructor (Device) to set up the protocol.
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }
    // This method is used to identify the type of the device (MotorDriver in this case).
    @Override
    public String getDevType()
    {
        return "MotorDriver"; // Return the device type string
    }

    //Abstract class to set motor speed in the sub classes
    public abstract void setMotorSpeed(int speed);
}
