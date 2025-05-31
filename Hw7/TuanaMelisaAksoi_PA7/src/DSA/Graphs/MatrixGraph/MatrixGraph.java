package DSA.Graphs.MatrixGraph;

import DSA.Graphs.GTUGraph;
import java.util.Collection;

/**
 * Implementation of GTUGraph interface using an adjacency matrix representation.
 * Each vertex's connections are stored in an AdjacencyVect.
 */
public class MatrixGraph implements GTUGraph{
    private AdjacencyVect[] adjacencyMatrix;
    private int vertexCount;

    /**
     * Creates an empty graph with no vertices.
     * 
     * Time Complexity: O(1)
     */
    public MatrixGraph() {
        this.vertexCount = 0;
        this.adjacencyMatrix = new AdjacencyVect[0];  // Empty array initially
    }

    /**
     * Creates a new graph with the specified number of vertices.
     * @param vertexCount The number of vertices in the graph
     * @throws IllegalArgumentException if vertexCount is negative
     * 
     * Time Complexity: O(n) where n is the vertex count
     */
    public MatrixGraph(int vertexCount) {
        if (vertexCount < 0) {
            throw new IllegalArgumentException("Vertex count cannot be negative");
        }
        this.vertexCount = vertexCount;
        this.adjacencyMatrix = new AdjacencyVect[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyMatrix[i] = new AdjacencyVect(vertexCount);
        }
    }

    /**
     * Returns the number of vertices in the graph.
     * @return The total number of vertices
     * 
     * Time Complexity: O(1)
     */
    @Override
    public int size() {
        return vertexCount;
    }

    /**
     * Checks if an edge exists between two vertices.
     * @param v1 The first vertex
     * @param v2 The second vertex
     * @return true if an edge exists between v1 and v2, false otherwise
     * @throws IndexOutOfBoundsException if either vertex index is out of bounds
     * 
     * Time Complexity: O(1)
     */
    @Override
    public Boolean getEdge(int v1, int v2) {
        if (v1 < 0 || v1 >= vertexCount || v2 < 0 || v2 >= vertexCount) {
            throw new IndexOutOfBoundsException("Vertex index out of bounds");
        }
        return adjacencyMatrix[v1].contains(v2);
    }

    /**
     * Adds an edge between two vertices.
     * @param v1 The first vertex
     * @param v2 The second vertex
     * @return true if the graph was modified, false otherwise
     * @throws IndexOutOfBoundsException if either vertex index is out of bounds
     * 
     * Time Complexity: O(1)
     */
    @Override
    public Boolean setEdge(int v1, int v2) {
        if (v1 < 0 || v1 >= vertexCount || v2 < 0 || v2 >= vertexCount) {
            throw new IndexOutOfBoundsException("Vertex index out of bounds");
        }
        boolean modified = false;
        if (adjacencyMatrix[v1].add(v2)) {
            modified = true;
        }
        if (adjacencyMatrix[v2].add(v1)) {
            modified = true;
        }
        return modified;
    }

    /**
     * Returns a collection of all neighbors of a vertex.
     * @param v The vertex to get neighbors for
     * @return Collection of vertex IDs that are neighbors of v
     * @throws IndexOutOfBoundsException if vertex index is out of bounds
     * 
     * Time Complexity: O(1)
     */
    @Override
    public Collection<Integer> getNeighbors(int v) {
        if (v < 0 || v >= vertexCount) {
            throw new IndexOutOfBoundsException("Vertex index out of bounds");
        }
        return adjacencyMatrix[v];
    }

    /**
     * Resets the graph with a new number of vertices.
     * @param vertexCount The new number of vertices
     * @throws IllegalArgumentException if vertexCount is negative
     * 
     * Time Complexity: O(n) where n is the new vertex count
     */
    @Override
    public void reset(int vertexCount) {
        if (vertexCount < 0) {
            throw new IllegalArgumentException("Vertex count cannot be negative");
        }
        this.vertexCount = vertexCount;
        adjacencyMatrix = new AdjacencyVect[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyMatrix[i] = new AdjacencyVect(vertexCount);
        }
    }

}
