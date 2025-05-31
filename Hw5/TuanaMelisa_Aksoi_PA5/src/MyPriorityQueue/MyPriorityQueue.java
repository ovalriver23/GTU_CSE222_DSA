package src.MyPriorityQueue;

/**
 * An interface representing a priority queue data structure.
 * Elements in the queue are ordered according to their natural ordering,
 * or by a Comparator provided at queue construction time.
 *
 * @param <T> the type of elements held in this priority queue
 *           which must implement Comparable interface
 */
public interface MyPriorityQueue <T extends Comparable<T>> {
    /**
     * Inserts the specified element into this priority queue.
     *
     * @param t the element to add
     */
    void add(T t);

    /**
     * Retrieves and removes the head of this queue,
     * or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    T poll();

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue contains no elements
     */
    Boolean isEmpty();
}
    