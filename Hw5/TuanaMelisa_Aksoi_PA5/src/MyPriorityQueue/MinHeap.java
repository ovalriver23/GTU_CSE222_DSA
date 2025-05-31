package src.MyPriorityQueue;
import java.util.ArrayList;

/**
 * A generic min-heap implementation that maintains elements in ascending order.
 * The heap is implemented using an ArrayList and supports basic priority queue operations.
 * 
 * @param <T> The type of elements stored in the heap, must implement Comparable
 */
public class MinHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private ArrayList<T> Heap;

    /**
     * Constructs an empty min-heap.
     * Time Complexity: O(1)
     */

    //Constructs an empty min-heap. 
    public MinHeap(){
        Heap = new ArrayList<>();
    }

    /**
     * Adds an element to the heap and maintains the heap property.
     * Time Complexity: O(log n) where n is the number of elements in the heap
     * 
     * @param item The element to be added to the heap
     */
    @Override
    //Adds an element to the heap.
    // Maintains the heap property by pushing the new element up.
    public void add(T item) {
        Heap.add(item);
        pushUp(Heap.size() - 1);
    }

    /**
     * Removes and returns the minimum element from the heap.
     * Time Complexity: O(log n) where n is the number of elements in the heap
     * 
     * @return The minimum element in the heap, or null if the heap is empty
     */
    @Override
    public T poll() {
        // Return null if heap is empty
        if (Heap.isEmpty()) return null;
        
        T result = Heap.get(0);
        swapItemsAt(0, Heap.size() - 1);
        Heap.remove(Heap.size() - 1);
        
        // Restore heap property
        if (!Heap.isEmpty()) {
            pushDown(0);
        }
        
        return result;
    }

    /**
     * Checks if the heap is empty.
     * Time Complexity: O(1)
     * 
     * @return true if the heap is empty, false otherwise
     */
    @Override
    public Boolean isEmpty(){
        return Heap.isEmpty();
    }

    /**
     * Compares two elements at the specified indices in the heap.
     * Time Complexity: O(1)
     * 
     * @param index1 The index of the first element
     * @param index2 The index of the second element
     * @return A negative integer, zero, or a positive integer if the first element
     *         is less than, equal to, or greater than the second element
     */
    private int compareItems(int index1, int index2){
        return Heap.get(index1).compareTo(Heap.get(index2));
    }

    /**
     * Swaps two elements at the specified indices in the heap.
     * Time Complexity: O(1)
     * 
     * @param index1 The index of the first element
     * @param index2 The index of the second element
     */
    private void swapItemsAt(int index1, int index2) {
        T temporary = Heap.get(index1);
        Heap.set(index1, Heap.get(index2));
        Heap.set(index2, temporary);
    }

    /**
     * Moves an element up the heap to maintain the heap property.
     * Time Complexity: O(log n) where n is the number of elements in the heap
     * 
     * @param index The index of the element to move up
     */
    private void pushUp(int index) {
        // Continue until we reach the root or the correct position
        while ((index > 0) && (index - 1) / 2 >= 0) {
            int parentIndex = (index - 1) / 2;
            // If current element is smaller than parent, swap them
            if (compareItems(index, parentIndex) < 0) {
                swapItemsAt(index, parentIndex);
            }
            // Move up to parent position
            index = parentIndex;
        }
    }

    /**
     * Moves an element down the heap to maintain the heap property.
     * Time Complexity: O(log n) where n is the number of elements in the heap
     * 
     * @param index The index of the element to move down
     */
    private void pushDown(int index){
        // Continue until we reach a leaf node
        while(2*index + 1 < Heap.size()){
            // Find which child is smaller
            int smallerChild = getSmallerChild(index);
            // If current element is greater than smaller child, swap them
            if(compareItems(index, smallerChild) > 0){
                swapItemsAt(index, smallerChild);
                // Move down to the child's position
                index = smallerChild;
            }else break; // Current element is in correct position
        }
    }
 
    /**
     * Returns the index of the smaller child of the element at the specified index.
     * Time Complexity: O(1)
     * 
     * @param index The index of the parent element
     * @return The index of the smaller child
     */
    private int getSmallerChild(int index){
        int left = 2*index + 1;
        int right = 2*index + 2;
        if(right >= Heap.size()) return left;
        
        if(compareItems(left, right) < 0) return left;
        else return right;
    }
}
