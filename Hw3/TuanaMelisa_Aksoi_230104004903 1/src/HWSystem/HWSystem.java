package src.HWSystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.util.Iterator;

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

/**
 * Main system class that manages hardware devices and their communication protocols.
 * This class provides functionality for:
 * - Managing different types of devices (sensors, displays, wireless I/O, motor drivers)
 * - Handling device connections and disconnections
 * - Controlling device states (ON/OFF)
 * - Reading sensor data
 * - Displaying information
 * - Managing wireless communication
 * - Controlling motor speeds
 */
public class HWSystem {
    /** List of all available communication ports with their protocol types */
    private ArrayList<Protocol> ports;
    /** List of all connected devices in the system */
    private ArrayList<Device> devices;

    /** Collections of devices */
    private LinkedList<MotorDriver> motorDrivers;
    private LinkedList<Display> displays;
    private LinkedList<Sensor> sensors;
    private LinkedList<WirelessIO> wirelessIOs;

    /** Maps port indices to their connected devices (null if empty) */
    private ArrayList<Device> devicesByPort;

    /** Maximum number of devices allowed by type (from configuration) */
    private int sensorLimit;
    private int displayLimit;
    private int wirelessIOlimit;
    private int motorDriverLimit;

    /** Tracks which device IDs are already in use (by device type) */
    private boolean[] sensorIdUsed;
    private boolean[] displayIdUsed;
    private boolean[] wirelessIdUsed;
    private boolean[] motorDriverIdUsed;

    /** Arrays for direct device access by ID*/
    private Sensor[] sensorArr;
    private Display[] displayArr;
    private WirelessIO[] wirelessArr;
    private MotorDriver[] motorDriverArr;

    /** Maps port indices to device IDs*/
    private int[] portToDeviceId;

    /** Maps IDs to their port IDs */
    private int[] sensorToPort;
    private int[] displayToPort;
    private int[] wirelessToPort;
    private int[] motorDriverToPort;

    /**
     * Creates a new hardware system and sets it up based on a configuration file.
     * The system starts by reading the configuration file which tells it:
     * - What communication ports are available and what protocols they use
     * - How many devices of each type it can handle
     * - The initial state of the system
     *
     *
     * @param filePath Path to the configuration file that contains the system settings
     */
    public HWSystem(String filePath) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();

        motorDrivers = new LinkedList<>();
        displays = new LinkedList<>();
        sensors = new LinkedList<>();
        wirelessIOs = new LinkedList<>();

        loadConfiguration(filePath);

        devicesByPort = new ArrayList<>(ports.size());

        for (int i = 0; i < ports.size(); i++) {
            devicesByPort.add(null);
        }

        sensorIdUsed = new boolean[sensorLimit];
        displayIdUsed = new boolean[displayLimit];
        wirelessIdUsed = new boolean[wirelessIOlimit];
        motorDriverIdUsed = new boolean[motorDriverLimit];


        sensorArr = new Sensor[sensorLimit];
        displayArr = new Display[displayLimit];
        wirelessArr = new WirelessIO[wirelessIOlimit];
        motorDriverArr = new MotorDriver[motorDriverLimit];


        portToDeviceId = new int[ports.size()];
        
        int i = 0;
        while (i < ports.size()) {
            portToDeviceId[i] = -1;
            i++;
        }

        sensorToPort = new int[sensorLimit];
        displayToPort = new int[displayLimit];
        wirelessToPort = new int[wirelessIOlimit];
        motorDriverToPort = new int[motorDriverLimit];

        int j = 0;
        while (j < sensorLimit) {
            sensorToPort[j] = -1;
            j++;
        }

        j = 0;
        while (j < displayLimit) {
            displayToPort[j] = -1;
            j++;
        }

        j = 0;
        while (j < wirelessIOlimit) {
            wirelessToPort[j] = -1;
            j++;
        }

        j = 0;
        while (j < motorDriverLimit) {
            motorDriverToPort[j] = -1;
            j++;
        }
    }

    /**
     * Reads the system setup from a file.
     * This method:
     * - Loads available communication ports 
     * - Sets how many devices of each type can be used
     * - Creates default limits if the file can't be read
     *
     * @param filePath Location of the configuration file
     */
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
                        if (port.equals("I2C")) ports.add(new I2C(i));
                        else if (port.equals("SPI")) ports.add(new SPI(i));
                        else if (port.equals("UART")) ports.add(new UART(i));
                        else if (port.equals("OneWire")) ports.add(new OneWire(i));
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
            System.err.println("Error reading configuration file: " + e.getMessage());

            sensorLimit = 1;
            displayLimit = 1;
            wirelessIOlimit = 1;
            motorDriverLimit = 1;
        }
    }
    
    /**
     * Connects a new device to the system.
     * - Make sure the port you want to use is available
     * - Check if the device ID you want to use is free
     * - Verify it hasn't reached its limit for that type of device
     * - Create the right kind of device based on its name
     * - Set up all the necessary connections and records
     *
     * @param devName What kind of device you're adding 
     * @param portID Which port you want to connect it to
     * @param devID A unique number to identify this device
     */
    public void addDev(String devName, int portID, int devID) {
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }

        if (devicesByPort.get(portID) != null) {
            System.err.println("Port is already occupied.");
            return;
        }

        Protocol protocol = ports.get(portID);

        if (devName.equals("DHT11")) {
            if (devID < 0 || devID >= sensorLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (sensorIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (sensors.size() >= sensorLimit) {
                System.err.println("Sensor limit reached.");
                return;
            }

            if (protocol instanceof OneWire) {
                Sensor sensor = new DHT11(protocol);

                sensorArr[devID] = sensor;

                sensors.add(sensor);

                sensorIdUsed[devID] = true;
                devices.add(sensor);
                devicesByPort.set(portID, sensor);

                portToDeviceId[portID] = devID;

                sensorToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for DHT11.");
            }
        } else if (devName.equals("BME280")) {
            if (devID < 0 || devID >= sensorLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (sensorIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (sensors.size() >= sensorLimit) {
                System.err.println("Sensor limit reached.");
                return;
            }

            if (protocol instanceof I2C || protocol instanceof SPI) {
                Sensor sensor = new BME280(protocol);

                sensorArr[devID] = sensor;

                sensors.add(sensor);

                sensorIdUsed[devID] = true;
                devices.add(sensor);
                devicesByPort.set(portID, sensor);

                portToDeviceId[portID] = devID;

                sensorToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for BME280.");
            }
        } else if (devName.equals("MPU6050")) {
            if (devID < 0 || devID >= sensorLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (sensorIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (sensors.size() >= sensorLimit) {
                System.err.println("Sensor limit reached.");
                return;
            }

            if (protocol instanceof I2C) {
                Sensor sensor = new MPU6050(protocol);

                sensorArr[devID] = sensor;

                sensors.add(sensor);

                sensorIdUsed[devID] = true;
                devices.add(sensor);
                devicesByPort.set(portID, sensor);

                portToDeviceId[portID] = devID;

                sensorToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for MPU6050.");
            }
        } else if (devName.equals("GY951")) {
            if (devID < 0 || devID >= sensorLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (sensorIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (sensors.size() >= sensorLimit) {
                System.err.println("Sensor limit reached.");
                return;
            }

            if (protocol instanceof SPI || protocol instanceof UART) {
                Sensor sensor = new GY951(protocol);

                sensorArr[devID] = sensor;

                sensors.add(sensor);

                sensorIdUsed[devID] = true;
                devices.add(sensor);
                devicesByPort.set(portID, sensor);

                portToDeviceId[portID] = devID;

                sensorToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for GY951.");
            }
        } else if (devName.equals("LCD")) {
            if (devID < 0 || devID >= displayLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (displayIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (displays.size() >= displayLimit) {
                System.err.println("Display limit reached.");
                return;
            }

            if (protocol instanceof I2C) {
                Display display = new LCD(protocol);

                displayArr[devID] = display;

                displays.add(display);

                displayIdUsed[devID] = true;
                devices.add(display);
                devicesByPort.set(portID, display);

                portToDeviceId[portID] = devID;

                displayToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for LCD.");
            }
        } else if (devName.equals("OLED")) {
            if (devID < 0 || devID >= displayLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (displayIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (displays.size() >= displayLimit) {
                System.err.println("Display limit reached.");
                return;
            }

            if (protocol instanceof SPI) {
                Display display = new OLED(protocol);

                displayArr[devID] = display;

                displays.add(display);

                displayIdUsed[devID] = true;
                devices.add(display);
                devicesByPort.set(portID, display);

                portToDeviceId[portID] = devID;

                displayToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for OLED.");
            }
        } else if (devName.equals("Bluetooth")) {
            if (devID < 0 || devID >= wirelessIOlimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (wirelessIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (wirelessIOs.size() >= wirelessIOlimit) {
                System.err.println("Wireless IO limit reached.");
                return;
            }

            if (protocol instanceof UART) {
                WirelessIO wireless = new Bluetooth(protocol);

                wirelessArr[devID] = wireless;

                wirelessIOs.add(wireless);

                wirelessIdUsed[devID] = true;
                devices.add(wireless);
                devicesByPort.set(portID, wireless);

                portToDeviceId[portID] = devID;

                wirelessToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for Bluetooth.");
            }
        } else if (devName.equals("Wifi")) {
            if (devID < 0 || devID >= wirelessIOlimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (wirelessIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (wirelessIOs.size() >= wirelessIOlimit) {
                System.err.println("Wireless IO limit reached.");
                return;
            }

            if (protocol instanceof UART || protocol instanceof SPI) {
                WirelessIO wireless = new Wifi(protocol);

                wirelessArr[devID] = wireless;

                wirelessIOs.add(wireless);

                wirelessIdUsed[devID] = true;
                devices.add(wireless);
                devicesByPort.set(portID, wireless);

                portToDeviceId[portID] = devID;

                wirelessToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for Wifi.");
            }
        } else if (devName.equals("PCA9685")) {
            if (devID < 0 || devID >= motorDriverLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (motorDriverIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (motorDrivers.size() >= motorDriverLimit) {
                System.err.println("Motor driver limit reached.");
                return;
            }

            if (protocol instanceof I2C) {
                MotorDriver motorDriver = new PCA9685(protocol);

                motorDriverArr[devID] = motorDriver;

                motorDrivers.add(motorDriver);

                motorDriverIdUsed[devID] = true;
                devices.add(motorDriver);
                devicesByPort.set(portID, motorDriver);

                portToDeviceId[portID] = devID;

                motorDriverToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for PCA9685.");
            }
        } else if (devName.equals("SparkFunMD")) {
            if (devID < 0 || devID >= motorDriverLimit) {
                System.err.println("Invalid device ID.");
                return;
            }

            if (motorDriverIdUsed[devID]) {
                System.err.println("Device ID already in use.");
                return;
            }

            if (motorDrivers.size() >= motorDriverLimit) {
                System.err.println("Motor driver limit reached.");
                return;
            }

            if (protocol instanceof SPI) {
                MotorDriver motorDriver = new SparkFunMD(protocol);

                motorDriverArr[devID] = motorDriver;

                motorDrivers.add(motorDriver);

                motorDriverIdUsed[devID] = true;
                devices.add(motorDriver);
                devicesByPort.set(portID, motorDriver);

                portToDeviceId[portID] = devID;

                motorDriverToPort[devID] = portID;
                
                System.out.println("Device added.");
            } else {
                System.err.println("Incompatible protocol for SparkFunMD.");
            }
        }
    }

    /**
     * Removes a device from the specified port in the system.
     * 
     * - Verify the port ID is valid
     * - Check if a device is present at the specified port
     * - Ensure the device is in OFF state before removal
     * - Remove the device from all tracking collections
     * - Clear all mappings and references to the device
     * - Free up the device ID for future use
     *
     * @param portID The port from which to remove the connected device
     */
    public void rmDev(int portID) {
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }

        Device deviceToRemove = devicesByPort.get(portID);

        if (deviceToRemove == null) {
            System.err.println("Port is empty.");
            return;
        }

        if (deviceToRemove.getState() == State.ON) {
            System.err.println("Device is active.");
            System.err.println("Command failed.");
            return;
        }

        int devID = -1;

        if (deviceToRemove instanceof Sensor) {
            devID = sensors.indexOf(deviceToRemove);
            if (devID >= 0 && devID < sensorLimit) {
                sensorIdUsed[devID] = false;
                sensorArr[devID] = null;
                sensorToPort[devID] = -1;
            }
            sensors.remove(deviceToRemove);
        } else if (deviceToRemove instanceof Display) {
            devID = displays.indexOf(deviceToRemove);
            if (devID >= 0 && devID < displayLimit) {
                displayIdUsed[devID] = false;
                displayArr[devID] = null;
                displayToPort[devID] = -1;
            }
            displays.remove(deviceToRemove);
        } else if (deviceToRemove instanceof WirelessIO) {
            devID = wirelessIOs.indexOf(deviceToRemove);
            if (devID >= 0 && devID < wirelessIOlimit) {
                wirelessIdUsed[devID] = false;
                wirelessArr[devID] = null;
                wirelessToPort[devID] = -1;
            }
            wirelessIOs.remove(deviceToRemove);
        } else if (deviceToRemove instanceof MotorDriver) {
            devID = motorDrivers.indexOf(deviceToRemove);
            if (devID >= 0 && devID < motorDriverLimit) {
                motorDriverIdUsed[devID] = false;
                motorDriverArr[devID] = null;
                motorDriverToPort[devID] = -1;
            }
            motorDrivers.remove(deviceToRemove);
        }

        devices.remove(deviceToRemove);
        devicesByPort.set(portID, null);

        portToDeviceId[portID] = -1;
        
        System.out.println("Device removed.");
    }

    /**
     * Activates the device connected to the specified port.
     * 
     * This method powers on the device, enabling its functionality based on its type.
     * 
     * @param portID The port identifier where the device is connected
     */
    public void turnON(int portID) {
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }

        Device deviceToControl = devicesByPort.get(portID);

        if (deviceToControl == null) {
            System.err.println("Port is empty.");
            return;
        }

        deviceToControl.turnOn();
    }

    /**
     * Deactivates the device connected to the specified port.
     * 
     * This method powers off the device, transitioning it to an inactive state.
     * Devices should be deactivated before removal from the system.
     * 
     * @param portID The port identifier where the device is connected
     */
    public void turnOFF(int portID) {
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }

        Device deviceToControl = devicesByPort.get(portID);

        if (deviceToControl == null) {
            System.err.println("Port is empty.");
            return;
        }

        deviceToControl.turnOff();
    }

    /**
     * Displays list of all ports in the system with their status.
     * 
     * For each port
     * - Port ID and protocol type
     * - Connection status (occupied/empty)
     * - Device information when connected (name, type, ID)
     * - Current operational state of connected devices (ON/OFF)
     * 
     */
    public void listPorts() {
        System.out.println("list of ports:");
        
        Iterator<Protocol> portIterator = ports.iterator();
        int portIndex = 0;
        
        while (portIterator.hasNext()) {
            Protocol protocol = portIterator.next();
            String result = portIndex + " " + protocol.getProtocolName() + " ";
            
            Device connectedDevice = devicesByPort.get(portIndex);
            
            if (connectedDevice != null) {
                result += "occupied " + connectedDevice.getName() + " "
                        + connectedDevice.getDevType() + " ";
                
                int devID = portToDeviceId[portIndex];
                
                result += devID + " " + connectedDevice.getState();
            } else {
                result += "empty";
            }
            
            System.out.println(result);
            portIndex++;
        }
    }

    /**
     * Lists all devices of the specified type currently connected to the system.
     * 
     * For each device, this method displays:
     * - Device name
     * - Device ID
     * - Port ID where it's connected
     * - Communication protocol used
     *
     * @param devType The category of devices to list (Sensor, Display, WirelessIO, or MotorDriver)
     */
    public void listDevType(String devType) {
        System.out.println("list of " + devType + "s:");
        
        if (devType.equals("Sensor")) {
            int[] deviceIndices = new int[sensors.size()];
            int count = 0;
            
            int i = 0;
            while (i < sensorLimit) {
                if (sensorArr[i] != null && count < sensors.size()) {
                    deviceIndices[count++] = i;
                }
                i++;
            }
            
            Iterator<Sensor> sensorIterator = sensors.iterator();
            count = 0;
            while (sensorIterator.hasNext()) {
                Sensor device = sensorIterator.next();
                int index = deviceIndices[count++];
                int portId = sensorToPort[index];
                if (portId != -1) {
                    Protocol protocol = ports.get(portId);
                    System.out.println(device.getName() + " " + index + " " + portId + " " + 
                        protocol.getProtocolName());
                }
            }
        } else if (devType.equals("Display")) {
            int[] deviceIndices = new int[displays.size()];
            int count = 0;
            
            int i = 0;
            while (i < displayLimit) {
                if (displayArr[i] != null && count < displays.size()) {
                    deviceIndices[count++] = i;
                }
                i++;
            }
            
            Iterator<Display> displayIterator = displays.iterator();
            count = 0;
            while (displayIterator.hasNext()) {
                Display device = displayIterator.next();
                int index = deviceIndices[count++];
                int portId = displayToPort[index];
                if (portId != -1) {
                    Protocol protocol = ports.get(portId);
                    System.out.println(device.getName() + " " + index + " " + portId + " " + 
                        protocol.getProtocolName());
                }
            }
        } else if (devType.equals("WirelessIO")) {
            int[] deviceIndices = new int[wirelessIOs.size()];
            int count = 0;
            
            int i = 0;
            while (i < wirelessIOlimit) {
                if (wirelessArr[i] != null && count < wirelessIOs.size()) {
                    deviceIndices[count++] = i;
                }
                i++;
            }
            
            Iterator<WirelessIO> wirelessIterator = wirelessIOs.iterator();
            count = 0;
            while (wirelessIterator.hasNext()) {
                WirelessIO device = wirelessIterator.next();
                int index = deviceIndices[count++];
                int portId = wirelessToPort[index];
                if (portId != -1) {
                    Protocol protocol = ports.get(portId);
                    System.out.println(device.getName() + " " + index + " " + portId + " " + 
                        protocol.getProtocolName());
                }
            }
        } else if (devType.equals("MotorDriver")) {
            int[] deviceIndices = new int[motorDrivers.size()];
            int count = 0;
            
            int i = 0;
            while (i < motorDriverLimit) {
                if (motorDriverArr[i] != null && count < motorDrivers.size()) {
                    deviceIndices[count++] = i;
                }
                i++;
            }

            
            Iterator<MotorDriver> motorIterator = motorDrivers.iterator();
            count = 0;
            while (motorIterator.hasNext()) {
                MotorDriver device = motorIterator.next();
                int index = deviceIndices[count++];
                int portId = motorDriverToPort[index];
                if (portId != -1) {
                    Protocol protocol = ports.get(portId);
                    System.out.println(device.getName() + " " + index + " " + portId + " " + 
                        protocol.getProtocolName());
                }
            }
        } else {
            System.err.println("Unknown device type: " + devType);
        }
    }

    /**
     * Retrieves and displays the current reading from a sensor device.
     * 
     * This method requests data from a specified sensor and outputs the 
     * formatted measurement data. The sensor must be in an active state
     * for this operation to succeed.
     *
     * @param devID The identifier of the sensor to read from
     */
    public void readSensor(int devID) {
        if (devID < 0 || devID >= sensorLimit) {
            System.err.println("Invalid device ID.");
            return;
        }

        Sensor sensor = sensorArr[devID];
        if (sensor == null) {
            System.err.println("Invalid device ID.");
            return;
        }

        if (sensor.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }

        System.out.println(sensor.getName() + " " + sensor.getDevType() + ": " + sensor.data2String());
    }

    /**
     * Displays text on the display
     * 
     * This method sends a string to be shown on the display identified by devID.
     * The display must be in active state (ON) for the operation to succeed.
     *
     * @param devID The identifier of the display device
     * @param data The text content to be displayed
     */
    public void printDisplay(int devID, String data) {
        if (devID < 0 || devID >= displayLimit) {
            System.err.println("Invalid device ID.");
            return;
        }

        Display display = displayArr[devID];

        if (display.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }

        display.printData(data);
    }

    /**
     * Receives data from a specified wireless device.
     * 
     * This method activates the receiving function on the target wireless device.
     * The device must be in an active state for this operation to succeed.
     *
     * @param devID The identifier of the wireless device to receive data from
     */
    public void readWireless(int devID) {
        if (devID < 0 || devID >= wirelessIOlimit) {
            System.err.println("Invalid device ID.");
            return;
        }

        WirelessIO wireless = wirelessArr[devID];

        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }

        wireless.recvData();
    }

    /** 
     * This method sends the provided string through a wireless connection.
     * The wireless device must be in an active state for this operation to succeed.
     *
     * @param devID The identifier of the wireless device to use for transmission
     * @param data The content to be transmitted
     */
    public void writeWireless(int devID, String data) {
        if (devID < 0 || devID >= wirelessIOlimit) {
            System.err.println("Invalid device ID.");
            return;
        }

        WirelessIO wireless = wirelessArr[devID];

        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }

        wireless.sendData(data);
    }

    /**
     * Sets the speed of a motor connected to the specified motor driver.
     * 
     * This method controls the motor speed by sending the specified value to 
     * the motor driver. The motor driver must be active (ON state) for this
     * operation to succeed.
     *
     * @param devID The identifier of the motor driver to control
     * @param speed The target speed value for the motor
     */
    public void setMotorSpeed(int devID, int speed) {
        if (devID < 0 || devID >= motorDriverLimit) {
            System.err.println("Invalid device ID.");
            return;
        }

        MotorDriver motor = motorDriverArr[devID];

        if (motor.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }

        motor.setMotorSpeed(speed);
    }

    /**
     * Safely closes all communication ports in the system.
     * This method ensures proper resource cleanup and should be called
     * before system shutdown.
     */
    public void closeAllPorts() {
        Iterator<Protocol> iterator = ports.iterator();
        while (iterator.hasNext()) {
            Protocol protocol = iterator.next();
            if (protocol != null) {
                protocol.close();
            }
        }
    }
}