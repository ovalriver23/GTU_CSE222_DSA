package Main;

import java.util.Scanner;
import PlanetSystem.PlanetSystem;

/**
 * Main class for the Space Planetary System application.
 * This class serves as the entry point and command-line interface for managing
 * a planetary system, allowing users to create systems, add planets and satellites,
 * and perform various operations on the system.
 */
public class Main {
    /**
     * Main entry point of the application.
     * Handles user input and executes commands for managing the planetary system.
     * The program continues running until the user enters the "exit" command.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlanetSystem system = new PlanetSystem();

        printMenu();

        while (scanner.hasNextLine()) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;
            
            if (input.isEmpty()) {
                System.err.println("Please enter the command.");
                continue;
            }

            String[] parts = input.split("\\s+");

            try {
                switch (parts[0]) {
                    case "create":
                        if (parts.length == 7 && parts[1].equals("planetSystem")) {
                            String name = parts[2];
                            double temp = Double.parseDouble(parts[3]);
                            double pressure = Double.parseDouble(parts[4]);
                            double humidity = Double.parseDouble(parts[5]);
                            double radiation = Double.parseDouble(parts[6]);
                            system.createPlanetSystem(name, temp, pressure, humidity, radiation);
                        } else {
                            System.err.println("Invalid create planetSystem command.");
                        }
                        break;

                    case "addPlanet":
                        if (parts.length == 7) {
                            String name = parts[1];
                            String parent = parts[2];
                            double temp = Double.parseDouble(parts[3]);
                            double pressure = Double.parseDouble(parts[4]);
                            double humidity = Double.parseDouble(parts[5]);
                            double radiation = Double.parseDouble(parts[6]);
                            system.addPlanet(name, parent, temp, pressure, humidity, radiation);
                        } else {
                            System.err.println("Invalid addPlanet command.");
                        }
                        break;

                    case "addSatellite":
                        if (parts.length == 7) {
                            String name = parts[1];
                            String parent = parts[2];
                            double temp = Double.parseDouble(parts[3]);
                            double pressure = Double.parseDouble(parts[4]);
                            double humidity = Double.parseDouble(parts[5]);
                            double radiation = Double.parseDouble(parts[6]);
                            system.addSatellite(name, parent, temp, pressure, humidity, radiation);
                        } else {
                            System.err.println("Invalid addSatellite command.");
                        }
                        break;

                    case "findRadiationAnomalies":
                        System.out.println("Anomalies: ");
                        if (parts.length == 2) {
                            double threshold = Double.parseDouble(parts[1]);
                            system.findRadiationAnomalies(threshold);
                        } else {
                            System.err.println("Invalid findRadiationAnomalies command.");
                        }
                        break;

                    case "getPathTo":
                        if (parts.length == 2) {
                            String target = parts[1];
                            System.out.println("Path to " + target + ":");
                            system.getPathTo(target);
                        } else {
                            System.err.println("Invalid getPathTo command.");
                        }
                        break;

                    case "printMissionReport":
                        if (parts.length == 1) {
                            system.printMissionReport();
                        } else if (parts.length == 2) {
                            system.printMissionReport(parts[1]);
                        } else {
                            System.err.println("Invalid printMissionReport command.");
                        }
                        break;

                    default:
                        System.err.println("Unknown command.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Mission control terminated.");
        scanner.close();
    }

    /**
     * Prints the menu of available commands to the console.
     * Displays all supported commands and their usage syntax.
     */
    private static void printMenu() {
        System.out.println("=== Deep Space Planetary System ===");
        System.out.println("Available commands:");
        System.out.println(" -create planetSystem <name> <temp> <pressure> <humidity> <radiation>");
        System.out.println(" -addPlanet <name> <parent> <temp> <pressure> <humidity> <radiation>");
        System.out.println(" -addSatellite <name> <parent> <temp> <pressure> <humidity> <radiation>");
        System.out.println(" -findRadiationAnomalies <threshold>");
        System.out.println(" -getPathTo <name>");
        System.out.println(" -printMissionReport");
        System.out.println(" -printMissionReport <name>");
        System.out.println(" -exit");
    }
}