package src.Main;

import src.HWSystem.HWSystem;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Processes commands to manage devices in the system.
 * 
 * All user commands are read and stored in a queue until "exit" is detected
 * Commands executed in the same order they were received
 * 
 * The system supports operations like adding/removing devices, turning devices on/off, 
 * listing ports and devices, reading sensors, printing to displays, and controlling motors.
 */
public class Main {

    /**
     * Reads commands from user input, queues them, and then executes them sequentially.
     * 
     * Initialize hardware system with configuration from file
     * Process commands one by one from the queue, parsing parameters as needed
     * Execute appropriate system operations based on command type
     * Handle errors including invalid formats and parameter validation
     * Close all ports when execution completes
     *
     * @param args Command line arguments, where args[0] is the path to the configuration file
     */
    public static void main(String[] args) {
        String configFile = args[0];
        HWSystem system = new HWSystem(configFile);

        Scanner scanner = new Scanner(System.in);
        boolean inputting = true;
        
        Queue<String> commandQueue = new LinkedList<>();

        while (inputting) {
            String input = scanner.nextLine();
            
            commandQueue.add(input);
            
            if (input.startsWith("exit")) {
                inputting = false;
            }
        }
        
        while (!commandQueue.isEmpty()) {
            String input = commandQueue.poll();
            String[] tokens = input.split("\\s+");

            try {
                if (tokens[0].equals("addDev")) {
                    if (tokens.length < 4) {
                        System.err.println("Invalid format.");
                    } else {
                        String devName = tokens[1];
                        int portID = Integer.parseInt(tokens[2]);
                        int devID = Integer.parseInt(tokens[3]);
                        system.addDev(devName, portID, devID);
                    }
                } else if (tokens[0].equals("rmDev")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.rmDev(portID);
                    }
                } else if (tokens[0].equals("turnON")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnON(portID);
                    }
                } else if (tokens[0].equals("turnOFF")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnOFF(portID);
                    }
                } else if (tokens[0].equals("list")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        if (tokens[1].equals("ports")) {
                            system.listPorts();
                        } else {
                            system.listDevType(tokens[1]);
                        }
                    }
                } else if (tokens[0].equals("readSensor")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        system.readSensor(devID);
                    }
                } else if (tokens[0].equals("printDisplay")) {
                    if (tokens.length < 3) {
                        System.err.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);

                        String data = "";
                        for (int i = 2; i < tokens.length; i++) {
                            data += tokens[i];
                            if (i < tokens.length - 1) {
                                data += " ";
                            }
                        }
                        system.printDisplay(devID, data);
                    }
                } else if (tokens[0].equals("readWireless")) {
                    if (tokens.length < 2) {
                        System.err.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        system.readWireless(devID);
                    }
                } else if (tokens[0].equals("writeWireless")) {
                    if (tokens.length < 3) {
                        System.err.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);

                        String data = "";
                        for (int i = 2; i < tokens.length; i++) {
                            data += tokens[i];
                            if (i < tokens.length - 1) {
                                data += " ";
                            }
                        }
                        system.writeWireless(devID, data);
                    }
                } else if (tokens[0].equals("setMotorSpeed")) {
                    if (tokens.length < 3) {
                        System.err.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        int speed = Integer.parseInt(tokens[2]);
                        system.setMotorSpeed(devID, speed);
                    }
                } else if (tokens[0].equals("exit")) {
                    system.closeAllPorts();
                    System.out.println("Exiting ..."); 
                } else {
                    System.err.println("Unknown command: " + tokens[0]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format.");
            } catch (Exception e) {
                System.err.println("Error processing command: " + e.getMessage()); 
            }
        scanner.close();
    }
}
}

