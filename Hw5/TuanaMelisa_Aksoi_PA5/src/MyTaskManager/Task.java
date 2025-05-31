package src.MyTaskManager;

/**
 * Represents a task in the task management system.
 * Tasks are comparable based on their user's priority and task ID.
 */
public class Task implements Comparable<Task> {
    User user;
    Integer id;

    /**
     * Constructs a new task with the specified user and ID.
     * Time Complexity: O(1)
     * 
     * @param user The user associated with this task
     * @param id The unique identifier for this task
     */
    public Task(User user, Integer id) {
        this.user = user;
        this.id = id;
    }

    /**
     * Returns a string representation of the task.
     * Time Complexity: O(1)
     * 
     * @return A string in the format "Task {id} User {userID}"
     */
    public String toString() {
        return "Task " + id + " User " + user.getID();
    }

    /**
     * Compares this task with another task based on user priority and task ID.
     * Tasks are first compared by their user's priority, and if equal,
     * they are compared by their task ID.
     * Time Complexity: O(1)
     * 
     * @param other The task to be compared with
     * @return A negative integer, zero, or a positive integer if this task
     *         is less than, equal to, or greater than the other task
     */
    @Override
    public int compareTo(Task other) {
        int thisUserPriority = this.user.getPriority();
        int otherUserPriority = other.user.getPriority();

        if (thisUserPriority < otherUserPriority) {
            return -1;
        } else if (thisUserPriority > otherUserPriority) {
            return 1;
        } else {
            return Integer.compare(this.id, other.id);
        }
    }
}
