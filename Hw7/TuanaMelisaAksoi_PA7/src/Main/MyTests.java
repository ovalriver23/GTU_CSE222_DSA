package Main;

import DSA.Sorting.*;
import DSA.Graphs.*;
import DSA.Graphs.MatrixGraph.*;
import java.util.Comparator;
import java.util.Arrays;

public class MyTests {
    public static void main(String[] args) {
        boolean allTestsPassed = true;
        
        if (testSorter()) {
            System.out.println("Sorter tests passed");
        } else {
            System.out.println("Sorter tests failed");
            allTestsPassed = false;
        }

        if (testMatrixGraph()) {
            System.out.println("MatrixGraph tests passed");
        } else {
            System.out.println("MatrixGraph tests failed");
            allTestsPassed = false;
        }

        if (testAdjacencyVect()) {
            System.out.println("AdjacencyVect tests passed");
        } else {
            System.out.println("AdjacencyVect tests failed");
            allTestsPassed = false;
        }

        if (allTestsPassed) {
            System.out.println("All tests passed successfully!");
        } else {
            System.out.println("Some tests failed!");
        }
    }

    private static boolean testSorter() {
        boolean allPassed = true;
        
        // Test arrays
        Integer[] arr1 = {5, 2, 8, 1, 9};
        Integer[] arr2 = {1, 2, 3, 4, 5};
        Integer[] arr3 = {5, 4, 3, 2, 1};
        Integer[] arr4 = {1};
        Integer[] arr5 = {};

        // Test each sorter
        GTUSorter[] sorters = {
            new MyInsertSort(),
            new MySelectSort(),
            new MyQuickSort(),
            new MyQuickSort(new MyInsertSort(), 10),
            new MyQuickSort(new MySelectSort(), 10)
        };

        for (GTUSorter sorter : sorters) {
            // Test arr1
            Integer[] testArr = new Integer[arr1.length];
            for (int i = 0; i < arr1.length; i++) {
                testArr[i] = arr1[i];
            }
            sorter.sort(testArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a.compareTo(b);
                }
            });
            if (!isSorted(testArr)) {
                System.out.println("Failed to sort arr1 with " + sorter.getClass().getSimpleName());
                allPassed = false;
            }

            // Test arr2 (already sorted)
            testArr = new Integer[arr2.length];
            for (int i = 0; i < arr2.length; i++) {
                testArr[i] = arr2[i];
            }
            sorter.sort(testArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a.compareTo(b);
                }
            });
            if (!isSorted(testArr)) {
                System.out.println("Failed to sort arr2 with " + sorter.getClass().getSimpleName());
                allPassed = false;
            }

            // Test arr3 (reverse sorted)
            testArr = new Integer[arr3.length];
            for (int i = 0; i < arr3.length; i++) {
                testArr[i] = arr3[i];
            }
            sorter.sort(testArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a.compareTo(b);
                }
            });
            if (!isSorted(testArr)) {
                System.out.println("Failed to sort arr3 with " + sorter.getClass().getSimpleName());
                allPassed = false;
            }

            // Test arr4 (single element)
            testArr = new Integer[arr4.length];
            for (int i = 0; i < arr4.length; i++) {
                testArr[i] = arr4[i];
            }
            sorter.sort(testArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a.compareTo(b);
                }
            });
            if (!isSorted(testArr)) {
                System.out.println("Failed to sort arr4 with " + sorter.getClass().getSimpleName());
                allPassed = false;
            }

            // Test arr5 (empty array)
            testArr = new Integer[arr5.length];
            for (int i = 0; i < arr5.length; i++) {
                testArr[i] = arr5[i];
            }
            sorter.sort(testArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a.compareTo(b);
                }
            });
            if (!isSorted(testArr)) {
                System.out.println("Failed to sort arr5 with " + sorter.getClass().getSimpleName());
                allPassed = false;
            }
        }

        return allPassed;
    }

    private static boolean testMatrixGraph() {
        boolean allPassed = true;
        
        // Test graph creation
        MatrixGraph graph = new MatrixGraph(5);
        if (graph.size() != 5) {
            System.out.println("Failed: Graph size should be 5");
            allPassed = false;
        }

        // Test edge operations
        if (!graph.setEdge(0, 1)) {
            System.out.println("Failed: Should be able to add edge (0,1)");
            allPassed = false;
        }

        if (!graph.getEdge(0, 1) || !graph.getEdge(1, 0)) {
            System.out.println("Failed: Edge (0,1) should exist");
            allPassed = false;
        }

        // Test neighbors
        if (!graph.getNeighbors(0).contains(1)) {
            System.out.println("Failed: Vertex 0 should have neighbor 1");
            allPassed = false;
        }

        // Test reset
        graph.reset(3);
        if (graph.size() != 3) {
            System.out.println("Failed: Graph size should be 3 after reset");
            allPassed = false;
        }

        // Test invalid operations
        try {
            graph.setEdge(-1, 0);
            System.out.println("Failed: Should throw exception for negative vertex");
            allPassed = false;
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            graph.setEdge(0, 5);
            System.out.println("Failed: Should throw exception for out of bounds vertex");
            allPassed = false;
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        return allPassed;
    }

    private static boolean testAdjacencyVect() {
        boolean allPassed = true;
        
        // Test creation
        AdjacencyVect vect = new AdjacencyVect(5);
        if (vect.size() != 0) {
            System.out.println("Failed: New AdjacencyVect should be empty");
            allPassed = false;
        }

        // Test adding vertices
        if (!vect.add(0)) {
            System.out.println("Failed: Should be able to add vertex 0");
            allPassed = false;
        }

        if (vect.add(0)) {
            System.out.println("Failed: Should not be able to add duplicate vertex");
            allPassed = false;
        }

        // Test contains
        if (!vect.contains(0)) {
            System.out.println("Failed: Should contain vertex 0");
            allPassed = false;
        }

        // Test remove
        if (!vect.remove(0)) {
            System.out.println("Failed: Should be able to remove vertex 0");
            allPassed = false;
        }

        if (vect.remove(0)) {
            System.out.println("Failed: Should not be able to remove non-existent vertex");
            allPassed = false;
        }

        // Test invalid operations
        try {
            vect.add(-1);
            System.out.println("Failed: Should throw exception for negative vertex");
            allPassed = false;
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            vect.add(5);
            System.out.println("Failed: Should throw exception for out of bounds vertex");
            allPassed = false;
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        return allPassed;
    }

    private static boolean isSorted(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}