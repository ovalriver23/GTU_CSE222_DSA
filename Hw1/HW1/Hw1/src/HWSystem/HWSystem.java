package src.HWSystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import src.HWSystem.Devices.Device;
import src.HWSystem.Devices.State;
import src.HWSystem.Devices.Display.Display;
import src.HWSystem.Devices.Display.LCD;
import src.HWSystem.Devices.Display.OLED;
import src.HWSystem.Devices.MotorDriver.MotorDriver;
import src.HWSystem.Devices.MotorDriver.PCA9685;
import src.HWSystem.Devices.MotorDriver.SparkFunMD;
import src.HWSystem.Devices.Sensor.Sensor;
import src.HWSystem.Devices.Sensor.IMUSensor.GY951;
import src.HWSystem.Devices.Sensor.IMUSensor.MPU6050;
import src.HWSystem.Devices.Sensor.TempSensor.BME280;
import src.HWSystem.Devices.Sensor.TempSensor.DHT11;
import src.HWSystem.Devices.WirelessIO.Bluetooth;
import src.HWSystem.Devices.WirelessIO.Wifi;
import src.HWSystem.Devices.WirelessIO.WirelessIO;
import src.HWSystem.Protocols.I2C;
import src.HWSystem.Protocols.OneWire;
import src.HWSystem.Protocols.Protocol;
import src.HWSystem.Protocols.SPI;
import src.HWSystem.Protocols.UART;

public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;

    private ArrayList<MotorDriver> motorDrivers;
    private ArrayList<Display> displays;
    private ArrayList<Sensor> sensors;
    private ArrayList<WirelessIO> wirelessIOs;

    private int sensorLimit;
    private int displayLimit;
    private int wirelessIOlimit;
    private int motorDriverLimit;

    public HWSystem(String filePath) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();

        motorDrivers = new ArrayList<>();
        displays = new ArrayList<>();
        sensors = new ArrayList<>();
        wirelessIOs = new ArrayList<>();

        loadConfiguration(filePath);
        }

        private void loadConfiguration(String filePath) {
            try {
                File configFile = new File(filePath);
                Scanner scanner = new Scanner(configFile);
        
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
        
                    if (line.startsWith("Port Configuration:")) {
                        String[] portsArr = line.substring("Port Configuration:".length()).trim().split(",");
                        for (int i = 0; i < portsArr.length; i++) {
                            String port = portsArr[i].trim();
                            if (port.equals("I2C")) ports.add(new I2C());
                            else if (port.equals("SPI")) ports.add(new SPI());
                            else if (port.equals("UART")) ports.add(new UART());
                            else if (port.equals("OneWire")) ports.add(new OneWire());
                        }
                    } else if (line.startsWith("# of sensors:")) {
                        sensorLimit = Integer.parseInt(line.substring("# of sensors:".length()).trim());
                    } else if (line.startsWith("# of displays:")) {
                        displayLimit = Integer.parseInt(line.substring("# of displays:".length()).trim());
                    } else if (line.startsWith("# of wireless adapters:")) {
                        wirelessIOlimit = Integer.parseInt(line.substring("# of wireless adapters:".length()).trim());
                    } else if (line.startsWith("# of motor drivers:")) {
                        motorDriverLimit = Integer.parseInt(line.substring("# of motor drivers:".length()).trim());
                    }
                }
        
                scanner.close();
            } catch (Exception e) {
                System.out.println("Error reading configuration file: " + e.getMessage());
        
                // Set default values in case of error
                sensorLimit = 1;
                displayLimit = 1;
                wirelessIOlimit = 1;
                motorDriverLimit = 1;
            }
        }

    // Check if a port is occupied
    private boolean isPortOccupied(int portID) {
        if (portID < 0 || portID >= ports.size()) {
            return false;
        }
        
        Protocol specificPort = ports.get(portID);
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getProtocol() == specificPort) {
                return true;
            }
        }
        return false;
    }

    // Add a device to the system
    public void addDev(String devName, int portID, int devID) {
        // Check port bounds
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Check if port is already occupied
        if (isPortOccupied(portID)) {
            System.out.println("Port is already occupied.");
            return;
        }
        
        Protocol protocol = ports.get(portID); 

        // Create the device based on its name and check compatibility with protocol
        if (devName.equals("DHT11")) {
            // Check for duplicate device ID directly within each case
            for (int i = 0; i < sensors.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check sensor limit
            if (sensors.size() >= sensorLimit) {
                System.out.println("Sensor limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof OneWire) {
                Sensor sensor = new DHT11(protocol);
                sensors.add(sensor);
                devices.add(sensor);
            } else {
                System.out.println("Incompatible protocol for DHT11.");
                return;
            }
            
        } 
        else if (devName.equals("BME280")) {
            // Check for duplicate device ID
            for (int i = 0; i < sensors.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check sensor limit
            if (sensors.size() >= sensorLimit) {
                System.out.println("Sensor limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof I2C || protocol instanceof SPI) {
                Sensor sensor = new BME280(protocol);
                sensors.add(sensor);
                devices.add(sensor);
            } else {
                System.out.println("Incompatible protocol for BME280.");
                return;
            }
            
        }
        else if (devName.equals("MPU6050")) {
            // Check for duplicate device ID
            for (int i = 0; i < sensors.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check sensor limit
            if (sensors.size() >= sensorLimit) {
                System.out.println("Sensor limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof I2C) {
                Sensor sensor = new MPU6050(protocol);
                sensors.add(sensor);
                devices.add(sensor);
            } else {
                System.out.println("Incompatible protocol for MPU6050.");
                return;
            }
            
        }
        else if (devName.equals("GY951")) {
            // Check for duplicate device ID
            for (int i = 0; i < sensors.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check sensor limit
            if (sensors.size() >= sensorLimit) {
                System.out.println("Sensor limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof SPI || protocol instanceof UART) {
                Sensor sensor = new GY951(protocol);
                sensors.add(sensor);
                devices.add(sensor);
            } else {
                System.out.println("Incompatible protocol for GY951.");
                return;
            }

        }
        else if (devName.equals("LCD")) {
            // Check for duplicate device ID
            for (int i = 0; i < displays.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check display limit
            if (displays.size() >= displayLimit) {
                System.out.println("Display limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof I2C) {
                Display display = new LCD(protocol);
                displays.add(display);
                devices.add(display);
            } else {
                System.out.println("Incompatible protocol for LCD.");
                return;
            }

        }
        else if (devName.equals("OLED")) {
            // Check for duplicate device ID
            for (int i = 0; i < displays.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check display limit
            if (displays.size() >= displayLimit) {
                System.out.println("Display limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof SPI) {
                Display display = new OLED(protocol);
                displays.add(display);
                devices.add(display);
            } else {
                System.out.println("Incompatible protocol for OLED.");
                return;
            }
             
        }
        else if (devName.equals("Bluetooth")) {
            // Check for duplicate device ID
            for (int i = 0; i < wirelessIOs.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check wireless limit
            if (wirelessIOs.size() >= wirelessIOlimit) {
                System.out.println("Wireless adapter limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof UART) {
                WirelessIO wireless = new Bluetooth(protocol);
                wirelessIOs.add(wireless);
                devices.add(wireless);
            } else {
                System.out.println("Incompatible protocol for Bluetooth.");
                return;
            }
            
        }
        else if (devName.equals("Wifi")) {
            // Check for duplicate device ID
            for (int i = 0; i < wirelessIOs.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check wireless limit
            if (wirelessIOs.size() >= wirelessIOlimit) {
                System.out.println("Wireless adapter limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof SPI || protocol instanceof UART) {
                WirelessIO wireless = new Wifi(protocol);
                wirelessIOs.add(wireless);
                devices.add(wireless);
            } else {
                System.out.println("Incompatible protocol for Wifi.");
                return;
            }

        }
        else if (devName.equals("PCA9685")) {
            // Check for duplicate device ID
            for (int i = 0; i < motorDrivers.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check motor driver limit
            if (motorDrivers.size() >= motorDriverLimit) {
                System.out.println("Motor driver limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof I2C) {
                MotorDriver moto = new PCA9685(protocol);
                motorDrivers.add(moto);
                devices.add(moto);
            } else {
                System.out.println("Incompatible protocol for PCA9685.");
                return;
            }
            
        }
        else if (devName.equals("SparkFunMD")) {
            // Check for duplicate device ID
            for (int i = 0; i < motorDrivers.size(); i++) {
                if (i == devID) {
                    System.out.println("Device ID already in use.");
                    return;
                }
            }
            
            // Check motor driver limit
            if (motorDrivers.size() >= motorDriverLimit) {
                System.out.println("Motor driver limit reached.");
                return;
            }
            
            // Check protocol compatibility
            if (protocol instanceof SPI) {
                MotorDriver moto = new SparkFunMD(protocol);
                motorDrivers.add(moto);
                devices.add(moto);
            } else {
                System.out.println("Incompatible protocol for SparkFunMD.");
                return;
            }
    
        }
        else {
            System.out.println("Unknown device: " + devName);
            return;
        }
        
    }

    // Remove a device
    public void rmDev(int portID) {
        // Check port bounds
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Device deviceToRemove = null;
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getProtocol() == ports.get(portID)) {
                deviceToRemove = device;
                break;
            }
        }
        
        // Check if port is empty
        if (deviceToRemove == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Check if device is ON
        if (deviceToRemove.getState() == State.ON) {
            System.out.println("Device is active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Remove from type-specific list
        if (deviceToRemove instanceof Sensor) {
            sensors.remove(deviceToRemove);
        } else if (deviceToRemove instanceof Display) {
            displays.remove(deviceToRemove);
        } else if (deviceToRemove instanceof WirelessIO) {
            wirelessIOs.remove(deviceToRemove);
        } else if (deviceToRemove instanceof MotorDriver) {
            motorDrivers.remove(deviceToRemove);
        }
        
        // Remove from connected devices list
        devices.remove(deviceToRemove);
    }

    // Turn a device ON
    public void turnON(int portID) {
        // Check port bounds
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Device deviceToControl = null;
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getProtocol() == ports.get(portID)) {
                deviceToControl = device;
                break;
            }
        }
        
        // Check if port is empty
        if (deviceToControl == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Turn device ON
        deviceToControl.turnOn();
    }

    // Turn a device OFF
    public void turnOFF(int portID) {
        // Check port bounds
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Device deviceToControl = null;
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getProtocol() == ports.get(portID)) {
                deviceToControl = device;
                break;
            }
        }
        
        // Check if port is empty
        if (deviceToControl == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Turn device OFF
        deviceToControl.turnOff();
    }

    // List all ports
    public void listPorts() {
        System.out.println("list of ports:");
        
        for (int i = 0; i < ports.size(); i++) {
            Protocol protocol = ports.get(i);
            String result = i + " " + protocol.getProtocolName() + " ";
            
            // Check if port is occupied
            boolean occupied = false;
            Device connectedDevice = null;
            
            for (int j = 0; j < devices.size(); j++) {
                Device device = devices.get(j);
                if (device.getProtocol() == protocol) {
                    occupied = true;
                    connectedDevice = device;
                    break;
                }
            }
            
            if (occupied && connectedDevice != null) {
                result += "occupied " + connectedDevice.getName() + " "
                        + connectedDevice.getDevType() + " ";
                
                // Find device ID
                int devID = -1;
                if (connectedDevice instanceof Sensor) {
                    devID = sensors.indexOf(connectedDevice);
                } else if (connectedDevice instanceof Display) {
                    devID = displays.indexOf(connectedDevice);
                } else if (connectedDevice instanceof WirelessIO) {
                    devID = wirelessIOs.indexOf(connectedDevice);
                } else if (connectedDevice instanceof MotorDriver) {
                    devID = motorDrivers.indexOf(connectedDevice);
                }
                
                result += devID + " " + connectedDevice.getState();
            } else {
                result += "empty";
            }
            
            System.out.println(result);
        }
    }

    private void printDev(Device device, int devId){
        int portId = -1;
        for(int j = 0; j<ports.size();j++){
            if(device.getProtocol() == ports.get(j)){
                portId = j;
                break;
            }
        }

        if(portId != -1){
            System.out.println(device.getName() + " " + devId + " " + portId + " " + 
            device.getProtocol().getProtocolName());
        }
    }
    

    // List devices of a specific type
    public void listDevType(String devType) {
        System.out.println("list of " + devType + "s:");
        
        // Select appropriate list based on device type
        if (devType.equals("Sensor")) {
            // List all sensors
            for (int i = 0; i < sensors.size(); i++) {
                Device device = sensors.get(i);
                printDev(device, i);
            }
        } else if (devType.equals("Display")) {
            // List all displays
            for (int i = 0; i < displays.size(); i++) {
                Device device = displays.get(i);
                printDev(device, i);
            }
        } else if (devType.equals("WirelessIO")) {
            // List all wireless IO devices
            for (int i = 0; i < wirelessIOs.size(); i++) {
                Device device = wirelessIOs.get(i);
                printDev(device, i);
            }
        } else if (devType.equals("MotorDriver")) {
            // List all motor drivers
            for (int i = 0; i < motorDrivers.size(); i++) {
                Device device = motorDrivers.get(i);
                printDev(device, i);
            }
        } else {
            System.out.println("Unknown device type: " + devType);
        }
    }

    // Read sensor data
    public void readSensor(int devID) {
        // Check device ID bounds
        if (devID < 0 || devID >= sensors.size()) {
            System.out.println("Invalid device ID.");
            return;
        }
        
        Sensor sensor = sensors.get(devID);
        
        // Check if sensor is ON
        if (sensor.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Display the protocol reading message first
        System.out.println(sensor.getProtocol().getProtocolName() + ": Reading.");
        
        // Read data
        System.out.println(sensor.getName() + " " + sensor.getDevType() + ": " + sensor.data2String());
    }

    // Print to display
    public void printDisplay(int devID, String data) {
        // Check device ID bounds
        if (devID < 0 || devID >= displays.size()) {
            System.out.println("Invalid device ID.");
            return;
        }
        
        Display display = displays.get(devID);
        
        // Check if display is ON
        if (display.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Print data
        display.printData(data);
    }

    // Read from wireless device
    public void readWireless(int devID) {
        // Check device ID bounds
        if (devID < 0 || devID >= wirelessIOs.size()) {
            System.out.println("Invalid device ID.");
            return;
        }
        
        WirelessIO wireless = wirelessIOs.get(devID);
        
        // Check if wireless device is ON
        if (wireless.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Read data
        wireless.recvData();
    }

    // Write to wireless device
    public void writeWireless(int devID, String data) {
        // Check device ID bounds
        if (devID < 0 || devID >= wirelessIOs.size()) {
            System.out.println("Invalid device ID.");
            return;
        }
        
        WirelessIO wireless = wirelessIOs.get(devID);
        
        // Check if wireless device is ON
        if (wireless.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Send data
        wireless.sendData(data);
    }

    // Set motor speed
    public void setMotorSpeed(int devID, int speed) {
        // Check device ID bounds
        if (devID < 0 || devID >= motorDrivers.size()) {
            System.out.println("Invalid device ID.");
            return;
        }
        
        MotorDriver motor = motorDrivers.get(devID);
        
        // Check if motor driver is ON
        if (motor.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Set speed
        motor.setMotorSpeed(speed);
    }    
}