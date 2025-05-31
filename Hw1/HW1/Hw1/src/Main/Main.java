package src.Main;

import src.HWSystem.HWSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //loading configuration from a file
        HWSystem system = new HWSystem("config.txt");

        //Create scanner object to read user input
        Scanner scanner = new Scanner(System.in);
        boolean running = true; //Flag to control while loop

        while (running) {
            System.out.print("Command: ");
            String input = scanner.nextLine(); //Read input
            String[] tokens = input.split("\\s+"); //Split the command into tokens

            try {
                if (tokens[0].equals("addDev")) {
                    if (tokens.length < 4) {
                        System.out.println("Invalid format.");
                    } else {
                        // Extract device name, port ID, and device ID from input
                        String devName = tokens[1];
                        int portID = Integer.parseInt(tokens[2]);
                        int devID = Integer.parseInt(tokens[3]);
                        system.addDev(devName, portID, devID); // Add the device to the system
                    }
                } else if (tokens[0].equals("rmDev")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.rmDev(portID); // Remove the device from the specified port
                    }
                } else if (tokens[0].equals("turnON")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnON(portID); // Turn on the device at the specified port
                    }
                } else if (tokens[0].equals("turnOFF")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnOFF(portID); // Turn off the device at the specified port
                    }
                    // Handle the "list" command to list all ports or devices of a specific type
                } else if (tokens[0].equals("list")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        // If "ports" is specified, list all ports
                        if (tokens[1].equals("ports")) {
                            system.listPorts();
                        } else {
                            // Otherwise, list all devices of the specified type
                            system.listDevType(tokens[1]);
                        }
                    }
                // Handle the "readSensor" command to read a sensor with a given device ID
                } else if (tokens[0].equals("readSensor")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        system.readSensor(devID);// Read the sensor data from the specified device ID
                    }
                } else if (tokens[0].equals("printDisplay")) {
                    if (tokens.length < 3) {
                        System.out.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);

                        // Concatenate the remaining tokens as the display string
                        String data = "";
                        for (int i = 2; i < tokens.length; i++) {
                            data += tokens[i];
                            if (i < tokens.length - 1) {
                                data += " ";
                            }
                        }
                        system.printDisplay(devID, data);// Print the specified data to the display
                    }
                } else if (tokens[0].equals("readWireless")) {
                    if (tokens.length < 2) {
                        System.out.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        system.readWireless(devID); // Read data from the wireless device with the specified ID
                    }
                } else if (tokens[0].equals("writeWireless")) {
                    if (tokens.length < 3) {
                        System.out.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);

                        // Concatenate the remaining tokens as the wireless data
                        String data = "";
                        for (int i = 2; i < tokens.length; i++) {
                            data += tokens[i];
                            if (i < tokens.length - 1) {
                                data += " ";
                            }
                        }
                        system.writeWireless(devID, data); // Send the specified data to the wireless device
                    }
                    // Handle the "setMotorSpeed" command to set the speed of a motor
                } else if (tokens[0].equals("setMotorSpeed")) {
                    if (tokens.length < 3) {
                        System.out.println("Invalid format.");
                    } else {
                        int devID = Integer.parseInt(tokens[1]);
                        int speed = Integer.parseInt(tokens[2]);
                        system.setMotorSpeed(devID, speed); // Set the motor speed for the specified device ID
                    }
                } else if (tokens[0].equals("exit")) {
                    running = false; // Stop the while loop and exit the program
                } else {
                    System.out.println("Unknown command: " + tokens[0]); // Handle unknown commands
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format."); // Handle invalid number format in user input
            } catch (Exception e) {
                System.out.println("Error processing command: " + e.getMessage()); 
            }
        }
        // Close the scanner object when done
        scanner.close();
    }
}
