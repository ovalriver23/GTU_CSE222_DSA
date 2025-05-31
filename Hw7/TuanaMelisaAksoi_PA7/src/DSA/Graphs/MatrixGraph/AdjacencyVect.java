package DSA.Graphs.MatrixGraph;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A specialized collection implementation for storing adjacency information in a graph.
 * Uses a boolean array to efficiently store vertex connections.
 */
public class AdjacencyVect implements Collection<Integer>{
    private boolean[] data; 
    private int capacity;  
    private int size;

    /**
     * Creates a new AdjacencyVect with the specified capacity.
     * @param capacity The maximum number of vertices that can be stored
     * 
     * Time Complexity: O(1)
     */
    public AdjacencyVect(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.data = new boolean[capacity];
    }
    
    /**
     * Adds a vertex to the collection if it's not already present.
     * @param e The vertex to add
     * @return true if the vertex was added, false if it was already present
     * @throws IndexOutOfBoundsException if the vertex index is out of bounds
     * 
     * Time Complexity: O(1)
     */
    @Override
    public boolean add(Integer e){
        if(e < 0 || e > capacity) throw new IndexOutOfBoundsException("Vertex index out of bounds: " + e);

        if(!data[e]){
            data[e] = true;
            size++;
            return true;
        }
        return false;
    }

    /**
     * Adds all vertices from the specified collection to this collection.
     * @param c The collection of vertices to add
     * @return true if this collection was modified
     * @throws NullPointerException if the specified collection is null
     * 
     * Time Complexity: O(m) where m is the size of the input collection
     */
    @Override 
    public boolean addAll(Collection<? extends Integer> c){
        if(c == null) throw new NullPointerException();

        boolean modified = false;
        for (Integer i: c){
            if(add(i)) modified = true;
        }

        return modified;
    }

    /**
     * Removes all vertices from this collection.
     * 
     * Time Complexity: O(n) where n is the capacity
     */
    @Override
    public void clear(){
        for(int i = 0; i < size; i++){
            data[i] = false;
        }
        size = 0;
    }

    /**
     * Checks if this collection contains the specified vertex.
     * @param o The vertex to check for
     * @return true if the vertex is present, false otherwise
     * 
     * Time Complexity: O(n)
     */
    @Override
    public boolean contains(Object o) {
        if (o == null || !(o instanceof Integer)) {
            return false;
        }
        Integer vertex = (Integer) o;
        if (vertex < 0 || vertex >= capacity) {
            return false;
        }
        return data[vertex];
    }

    /**
     * Checks if this collection contains all vertices from the specified collection.
     * @param c The collection of vertices to check for
     * @return true if all vertices are present, false otherwise
     * 
     * Time Complexity: O(m) where m is the size of the input collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    /**
     * Checks if this collection is empty.
     * @return true if there are no vertices, false otherwise
     * 
     * Time Complexity: O(1)
     */
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Returns an iterator over the vertices in this collection.
     * @return An iterator that traverses the vertices in ascending order
     * 
     * Time Complexity: O(1)
     */
    @Override
    public Iterator<Integer> iterator(){
        return new AdjacencyIterator();
    }

    /**
     * Removes the specified vertex from this collection.
     * @param o The vertex to remove
     * @return true if the vertex was removed, false if it wasn't present
     * 
     * Time Complexity: O(1)
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (o.equals(i)) {
                if (i < 0 || i > capacity) {
                    return false;
                }
                if (data[i]) {  
                    data[i] = false;
                    size--;
                    return true;
                }
                return false;  
            }
        }
        return false;
    }

    /**
     * Removes all vertices from this collection that are also in the specified collection.
     * @param c The collection of vertices to remove
     * @return true if this collection was modified
     * @throws NullPointerException if the specified collection is null
     * 
     * Time Complexity: O(m) where m is the size of the input collection
     */
    @Override
    public boolean removeAll(Collection<?> c){
        if (c == null) {
            throw new NullPointerException();
        }

        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retains only the vertices in this collection that are also in the specified collection.
     * @param c The collection of vertices to retain
     * @return true if this collection was modified
     * @throws NullPointerException if the specified collection is null
     * 
     * Time Complexity: O(n) where n is the capacity
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        
        boolean modified = false;
        for (int i = 0; i < capacity; i++) {
            if (data[i] && !c.contains(i)) {
                data[i] = false;
                size--;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Returns the number of vertices in this collection.
     * @return The number of vertices
     * 
     * Time Complexity: O(1)
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * Returns an array containing all vertices in this collection.
     * @return An array containing all vertices in ascending order
     * 
     * Time Complexity: O(n) where n is the capacity
     */
    @Override 
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (int i = 0; i < capacity; i++) {
            if (data[i]) {
                result[index++] = i;
            }
        }
        return result;
    }

    /**
     * This operation is not supported.
     * @throws UnsupportedOperationException always
     * 
     * Time Complexity: O(1)
     */
    @Override 
    public <T> T[] toArray(T[] a){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Iterator implementation for traversing vertices in ascending order.
     * 
     * Time Complexity:
     * - hasNext: O(n) worst case where n is the capacity
     * - next: O(n) worst case where n is the capacity
     * 
     */
    private class AdjacencyIterator implements Iterator<Integer> {
        private int index = -1; 

        /**
         * Checks if there are more vertices to iterate over.
         * @return true if there are more vertices, false otherwise
         * 
         * Time Complexity: O(n) worst case where n is the capacity
         */
        @Override
        public boolean hasNext() {
            int tempIndex = index + 1;  
            while (tempIndex < capacity) {
                if (data[tempIndex]) return true;
                tempIndex++;
            }
            return false;
        }

        /**
         * Returns the next vertex in the iteration.
         * @return The next vertex
         * @throws NoSuchElementException if there are no more vertices
         * 
         * Time Complexity: O(n) worst case where n is the capacity
         */
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index++;  
            while (!data[index]){ 
                index++;
            }
            return index;
        }
    }
}
