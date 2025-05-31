package src.HWSystem.Devices.Sensor.IMUSensor;

import src.HWSystem.Protocols.Protocol;

import src.HWSystem.Devices.State;

public  class GY951 extends IMUSensor{
    public GY951(Protocol protocol){
        super(protocol);
        
    }

    @Override
    public void turnOn(){
        state = State.ON;

        System.out.printf("%s: Turning ON\n", getName());

        getProtocol().write("turnON");
    }

    @Override
    public void turnOff(){
        state = State.OFF;

        System.out.printf("%s: Turnign Off\n", getName());

        getProtocol().write("turnOFF");
    }


    @Override
    public String getName(){
        return "GY951";
    }

    @Override
    public float getAccel(){
        getProtocol().read();
        return (float)Math.random() * 100.0f;
    }

    public float getRot(){
        getProtocol().read();
        return (float)Math.random() * 100.0f;
    }
}
