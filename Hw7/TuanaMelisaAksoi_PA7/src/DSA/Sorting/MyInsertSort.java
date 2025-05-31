package DSA.Sorting;

import java.util.Comparator;

/**
 * Implementation of insertion sort algorithm.
 * Builds the final sorted array one item at a time by comparing each new item
 * with the already sorted portion and inserting it at the correct position.
 */
public class MyInsertSort extends GTUSorter{
    /**
     * Creates a new insertion sort instance.
     * 
     * Time Complexity: O(1)
     */
    public MyInsertSort(){
        super();
    }

    /**
     * Sorts a portion of an array using insertion sort algorithm.
     * @param <T> The type of elements in the array
     * @param table The array to be sorted
     * @param start The starting index (inclusive)
     * @param end The ending index (exclusive)
     * @param comparator The comparator to determine the order of elements
     * 
     * Time Complexity:
     * - Best case: O(n) when array is nearly sorted
     * - Average case: O(n²)
     * - Worst case: O(n²) when array is reverse sorted
     */
    protected <T> void sort(T[] table, int start, int end, Comparator<T> comparator) {
        int n = end - start;
        for (int nextPos = start + 1; nextPos < end; nextPos++) {
            T nextVal = table[nextPos];
            int i = nextPos;

            
            while (i > start && comparator.compare(table[i - 1], nextVal) > 0) {
                table[i] = table[i - 1];
                i--;
            }

            table[i] = nextVal; 
        }
    }
}
