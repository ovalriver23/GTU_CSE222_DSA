package src.MyTaskManager;

import src.MyPriorityQueue.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced version of TaskManager with improved structure and functionality.
 * Manages tasks with different priority levels based on user configurations.
 */
public class TaskManager {
    private MyPriorityQueue<Task> tasks;  // Priority queue to store tasks
    private List<User> users;             // List to store user information
    private int taskIdCounter;            // Counter to assign unique task IDs
    
    /**
     * Constructs a new TaskManagerV2 instance.
     * Initializes the task queue and user list.
     * Time Complexity: O(1)
     */
    public TaskManager() {
        tasks = new MinHeap<>();
        users = new ArrayList<>();
        taskIdCounter = 0;
    }
    
    /**
     * Loads user configuration from a file.
     * Time Complexity: O(n) where n is the number of lines in the file
     * 
     * @param filePath Path to the configuration file
     * @return true if configuration was loaded successfully, false otherwise
     */
    public boolean loadUserConfig(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int userId = 0;
            
            while ((line = reader.readLine()) != null) {
                // Remove comments if present
                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//")).trim();
                }
                
                if (!line.isEmpty()) {
                    try {
                        int priority = Integer.parseInt(line.trim());
                        users.add(new User(userId, priority));  // Create user with priority
                        userId++;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid priority value in config file: " + line);
                        return false;
                    }
                }
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Processes input commands from standard input.
     * Time Complexity: O(m * log n) where m is the number of commands and n is the number of tasks
     */
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Check if execution command was given
                if (line.equals("execute") || line.equals("Execute")) {
                    executeTasks();
                    break;
                }
                
                try {        
                    if (!line.isEmpty()) {
                        int userId = Integer.parseInt(line);
                        addTask(userId);  // Add task for specified user
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid user ID: " + line);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("User ID out of range: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
    
    /**
     * Adds a new task for the specified user.
     * Time Complexity: O(log n) where n is the number of tasks
     * 
     * @param userId ID of the user for whom to add the task
     * @return true if task was added successfully, false otherwise
     */
    public boolean addTask(int userId) {
        if (userId < 0 || userId >= users.size()) {
            System.err.println("Invalid user ID: " + userId);
            return false;
        }
        
        User user = users.get(userId);
        Task task = new Task(user, taskIdCounter++);  // Create task with unique ID
        tasks.add(task);  // Add to priority queue
        return true;
    }
    
    /**
     * Executes all tasks in priority order.
     * Time Complexity: O(n * log n) where n is the number of tasks
     */
    public void executeTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to execute.");
            return;
        }
        
        System.out.println("Executing tasks in priority order:");
        while (!tasks.isEmpty()) {
            Task task = tasks.poll();  // Get highest priority task
            System.out.println(task.toString());  // Display task information
        }
    }
        
}