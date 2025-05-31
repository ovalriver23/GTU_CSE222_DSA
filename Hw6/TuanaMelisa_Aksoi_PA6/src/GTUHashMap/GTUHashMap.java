package GTUHashMap;
import Entry.Entry;

// A custom HashMap implementation using quadratic probing for collision resolution
public class GTUHashMap<K, V> {  
    // Array of entries to store key-value pairs
    private Entry<K, V>[] table; 
    // Current number of elements in the map
    private int size; 
    // Current capacity of the table
    private int capacity;
    // Threshold for load factor that triggers rehashing
    private double loadFactorThreshold = 0.75;
    // Counter for number of collisions
    private int collision;

    // Initialize empty hash map with capacity 11
    public GTUHashMap(){
        this.capacity = 11;
        this.size = 0;
        this.table = new Entry[capacity];
        this.collision = 0;
    }  

    // Insert or update a key-value pair in the map
    public void put(K key, V value){
        // Check for null key
        if(key == null){
            throw new NullPointerException("Key cannot be null");
        }

        // Calculate load factor and rehash if needed
        double loadFactor = (double) (size + 1) / table.length;
        if(loadFactor > loadFactorThreshold){
            rehash();
        }

        // Calculate hash index
        int hashCode = key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode; 
        }
        int index = hashCode % table.length;

        // Quadratic probing to find insertion spot
        int i = 0;
        int deletedIndex = -1;

        while(i < table.length){
            int probeIndex = (index + (i*i)) % table.length;

            if (table[probeIndex] == null) {
                // Found empty spot, insert here
                if (deletedIndex != -1) {
                    table[deletedIndex] = new Entry<>(key, value);
                } else {
                    table[probeIndex] = new Entry<>(key, value);
                }
                size++;
                return;
            } else {
                // Count collision if spot is taken by different key
                if (!table[probeIndex].isDeleted && !table[probeIndex].key.equals(key)) {
                    collision++;
                }

                // Handle deleted entry or update existing key
                if (table[probeIndex].isDeleted) {
                    if (deletedIndex == -1) deletedIndex = probeIndex;
                } else if (table[probeIndex].key.equals(key)) {
                    table[probeIndex].value = value;
                    return;
                }
            }
            i++;
        }

        // If we found a deleted spot, use it
        if(deletedIndex != -1){
            table[deletedIndex] = new Entry<>(key, value);
            size++;
        }else{
            // If no spot found, rehash and try again
            rehash();
            put(key, value);
        }
    }

    // Return number of collisions that occurred
    public int getCollisions() {
        return collision;
    }

    // Retrieve value for given key
    public V get(K key){
        if(key == null){
            throw new NullPointerException("Key cannot be null");
        }

        // Calculate hash index
        int hashCode = key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode; 
        }
        int index = hashCode % table.length;

        // Search for key using quadratic probing
        for(int i = 0; i < table.length; i++){
            int probeIndex = (index + (i*i)) % table.length;
            
            if(table[probeIndex] == null){
                return null;
            }
            
            if(!table[probeIndex].isDeleted && table[probeIndex].key.equals(key)){
                return table[probeIndex].value;
            }
        }

        return null;
    }  

    // Remove key-value pair from map
    public void remove(K key){
        if(key == null){
            throw new NullPointerException("Key cannot be null");
        }

        // Calculate hash index
        int hashCode = key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode; 
        }
        int index = hashCode % table.length;

        // Search and mark as deleted if found
        for(int i = 0; i < table.length; i++){
            int probeIndex = (index + (i*i)) % table.length;
            
            if(table[probeIndex] == null){
                return;
            }
            
            if(!table[probeIndex].isDeleted && table[probeIndex].key.equals(key)){
                table[probeIndex].isDeleted = true;
                size--;
                return;
            }
        }
    }

    // Check if key exists in map
    public boolean containsKey(K key){ 
        if(key == null){
            throw new NullPointerException("Key cannot be null");
        }

        // Calculate hash index
        int hashCode = key.hashCode();
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        int index = hashCode % table.length;

        // Search for key using quadratic probing
        for(int i = 0; i < table.length; i++){
            int probeIndex = (index + (i*i)) % table.length;

            if(table[probeIndex] == null) return false;

            if(!table[probeIndex].isDeleted && table[probeIndex].key.equals(key)) return true;
        }
        return false; 
    } 

    // Return current number of elements
    public int size(){ 
        return size;
    }  

    // Rehash the table when load factor threshold is exceeded
    private void rehash(){
        // Find next prime number for new capacity
        int newCapacity = findNextPrime(capacity * 2);
        Entry<K, V>[] newTable = new Entry[newCapacity];
        
        // Save old table and reset counters
        Entry<K, V>[] oldTable = table;
        int oldCapacity = capacity;

        table = newTable;
        capacity = newCapacity;
        size = 0;
        collision = 0;
        
        // Reinsert all non-deleted entries
        for (int i = 0; i < oldCapacity; i++) {
            if (oldTable[i] != null && !oldTable[i].isDeleted) {
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }

    // Find next prime number greater than n
    private int findNextPrime(int n) {
        if (n <= 1) return 2;
        
        int prime = n;
        boolean found = false;
        
        while (!found) {
            prime++;
            if (isPrime(prime)) {
                found = true;
            }
        }
        
        return prime;
    }
    
    // Check if a number is prime
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
