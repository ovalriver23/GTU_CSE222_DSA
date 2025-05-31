package src.MyTaskManager;

/**
 * Represents a user in the task management system.
 * Each user has a unique ID and a priority level.
 */
public class User {
    Integer id;
    Integer priority;

    /**
     * Constructs a new user with the specified ID and priority.
     * Time Complexity: O(1)
     * 
     * @param id The unique identifier for this user
     * @param priority The priority level of this user
     */
    public User(Integer id, Integer priority) {
        if(priority < 0){
            System.err.println("The priority cant be below zero");
            this.priority = 0;
        }
        this.id = id;
        this.priority = priority;
    }

    /**
     * Returns the ID of this user.
     * Time Complexity: O(1)
     * 
     * @return The user's ID
     */
    public Integer getID() {
        return this.id;
    }

    /**
     * Returns the priority level of this user.
     * Time Complexity: O(1)
     * 
     * @return The user's priority level
     */
    public Integer getPriority() {
        return this.priority;
    }
}
