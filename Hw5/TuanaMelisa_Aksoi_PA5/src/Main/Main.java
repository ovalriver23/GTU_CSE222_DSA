package src.Main;

import src.MyTaskManager.TaskManager;

/**
 * This class handles the initialization of the TaskManager and processes command-line arguments.
 * 
 * Time Complexity:
 * - Overall: O(n), where n is the size of the configuration file
 * - The complexity is dominated by the loadUserConfig method call
 * 
 */
public class Main {
    /**
     * The main entry point for the Task Manager application.
     * 
     * @param args Command line arguments where args[0] should be the path to the configuration file
     * 
     * Time Complexity:
     * - O(1) for argument checking
     * - O(n) for loading user configuration, where n is the size of the config file
     * - O(1) for TaskManager instantiation
     * 
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java TaskManager <config_file_path>");
            return;
        }
        
        TaskManager manager = new TaskManager();
        
        if (manager.loadUserConfig(args[0])) {
            manager.run();
        }
    }
}