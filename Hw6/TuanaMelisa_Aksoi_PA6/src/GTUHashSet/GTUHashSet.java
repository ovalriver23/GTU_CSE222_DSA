package GTUHashSet;
import GTUHashMap.GTUHashMap;

// A custom HashSet implementation using GTUHashMap as the underlying data structure
public class GTUHashSet<E> {
    // Dummy value used as a placeholder in the map since we only care about keys
    private static final Object WORD = new Object();
    // Internal map to store the set elements
    private GTUHashMap<E, Object> map;

    // Create new empty set
    public GTUHashSet() { map = new GTUHashMap<>(); }

    // Add element to the set
    public void add(E element) { map.put(element, WORD); }

    // Remove element from the set
    public void remove(E element) { map.remove(element); }

    // Check if element exists in the set
    public boolean contains(E element) { return map.containsKey(element); }
    
    // Return current number of elements in the set
    public int size() { return map.size(); }

    // Get number of collisions that occurred in the underlying map
    public int getCollisionCount() { return map.getCollisions(); }
}