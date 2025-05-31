package Entry;

// Generic class for storing key-value pairs
public class Entry<K, V> {
    // The key of the entry
    public K key;
    // The value associated with the key
    public V value;
    // Flag to mark if entry is deleted (used in hash tables)
    public boolean isDeleted;

    // Constructor to create a new entry with given key and value
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.isDeleted = false;
    }

    // Getter for key
    public K getK(){
        return key;
    }

    // Setter for key
    public void setK(K newKey){
        this.key = newKey;
    }
    
    // Getter for value
    public V getV(){
        return value;
    }

    // Setter for value
    public void setV(V newValue){
        this.value = newValue;
    }
}