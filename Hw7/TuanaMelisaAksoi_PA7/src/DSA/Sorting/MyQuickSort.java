package DSA.Sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * Implementation of quicksort algorithm with optional hybrid sorting.
 * Uses a random pivot selection strategy and can switch to a different
 * sorting algorithm for small partitions to improve performance.
 */
public class MyQuickSort extends GTUSorter {
    private Random random;
    private GTUSorter subSorter;
    private int partitionLimit;

    /**
     * Creates a new quicksort instance with hybrid sorting capability.
     * @param subSorter The sorter to use for small partitions
     * @param partitionLimit The size limit below which to use subSorter
     * 
     * Time Complexity: O(1)
     */
    public MyQuickSort(GTUSorter subSorter, int partitionLimit) {
        super();
        this.random = new Random();
        this.subSorter = subSorter;
        this.partitionLimit = partitionLimit;
    }

    /**
     * Creates a new quicksort instance that uses only quicksort algorithm.
     * 
     * Time Complexity: O(1)
     */
    public MyQuickSort() {
        super();
        this.random = new Random();
        this.subSorter = null;
        this.partitionLimit = 0;
    }

    /**
     * Sorts a portion of an array using quicksort algorithm.
     * For small partitions, uses the subSorter if configured.
     * @param <T> The type of elements in the array
     * @param table The array to be sorted
     * @param start The starting index (inclusive)
     * @param end The ending index (exclusive)
     * @param comparator The comparator to determine the order of elements
     * 
     * Time Complexity:
     * - Best case: O(n log n) with good pivot selection
     * - Average case: O(n log n)
     * - Worst case: O(n²) with poor pivot selection
     */
    protected <T> void sort(T[] table, int start, int end, Comparator<T> comparator) {
        if (start >= end - 1) return;

        int size = end - start;
        if (subSorter != null && size <= partitionLimit) {
            subSorter.sort(table, start, end, comparator);
            return;
        }

        int pivotIndex = partition(table, start, end - 1, comparator);
        sort(table, start, pivotIndex, comparator);
        sort(table, pivotIndex + 1, end, comparator);
    }

    /**
     * Partitions the array around a randomly selected pivot.
     * @param <T> The type of elements in the array
     * @param table The array to be partitioned
     * @param first The starting index of the partition
     * @param last The ending index of the partition
     * @param comparator The comparator to determine the order of elements
     * @return The final position of the pivot element
     * 
     * Time Complexity: O(n) where n is the size of the partition
     */
    private <T> int partition(T[] table, int first, int last, Comparator<T> comparator) {
        int pivotIndex = first + random.nextInt(last - first + 1);
        T temp = table[first];
        table[first] = table[pivotIndex];
        table[pivotIndex] = temp;
        
        T pivot = table[first];
        int left = first + 1;
        int right = last;

        while (left <= right) {
            while (left <= right && comparator.compare(table[left], pivot) <= 0) {
                left++;
            }
            while (left <= right && comparator.compare(table[right], pivot) > 0) {
                right--;
            }

            if (left < right) {
                temp = table[left];
                table[left] = table[right];
                table[right] = temp;
            }
        }

        
        temp = table[first];
        table[first] = table[right];
        table[right] = temp;

        return right; 
    }
} 