package GTUArrayList;


 //A custom implementation of an ArrayList-like data structure that stores String elements.

public class GTUArrayList {
    //The string array to store the elements
    private String[] data;
    
    //The number of elements currently stored in the list
    private int size;
    
    //The current capacity of the underlying array
    private int capacity;

    //Constructs an empty GTUArrayList with an initial capacity of 10.
    public GTUArrayList() {
        capacity = 10;
        data = new String[capacity];
        size = 0;
    }

    //Adds a new element to the end of the list.
    //If the list is full, the capacity is automatically increased.
    public void add(String str) {
        if (size >= capacity) {
            extendCapacity();
        }
        data[size] = str;
        size++;
    }


    //Doubles the capacity of the array when it becomes full.
    //This method is called automatically when needed.
    private void extendCapacity() {
        capacity = capacity * 2;
        String[] newData = new String[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

     //Returns the number of elements in the list.
    public int size(){
        return size;
    }

     //Returns the element at the specified position in the list.
    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }
}

