package DSA.Sorting;

import java.util.Comparator;

/**
 * Implementation of selection sort algorithm.
 * Repeatedly selects the smallest element from the unsorted portion
 * and places it at the beginning of the sorted portion.
 */
public class MySelectSort extends GTUSorter{
    /**
     * Creates a new selection sort instance.
     * 
     * Time Complexity: O(1)
     */
    public MySelectSort(){
        super();
    }

    /**
     * Sorts a portion of an array using selection sort algorithm.
     * @param <T> The type of elements in the array
     * @param table The array to be sorted
     * @param start The starting index (inclusive)
     * @param end The ending index (exclusive)
     * @param comparator The comparator to determine the order of elements
     * 
     * Time Complexity: O(n²) where n is the number of elements to sort
     */
    protected <T> void sort(T[] table, int start, int end, Comparator<T> comparator) {
        for(int fill = start; fill < end - 1; fill++){
            int posMin = fill;
            for(int next = fill + 1; next < end; next++){
                if(comparator.compare(table[next], table[posMin]) < 0){
                    posMin = next;
                }

            }
 
            if(posMin != fill){
                T temp = table[fill];
                table[fill] = table[posMin];
                table[posMin] = temp;
            }
        }
    }
}


