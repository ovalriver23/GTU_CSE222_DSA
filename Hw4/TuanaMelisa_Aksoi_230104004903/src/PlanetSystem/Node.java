package PlanetSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the planetary system hierarchy.
 * A node can be a star, planet, or satellite (moon) and contains sensor data
 * about environmental conditions at that celestial body.
 */
public class Node {
    private String name;
    private String type; 
    private SensorData sensorData;
    private List<Node> children;
    
    /**
     * Creates a new Node with specified parameters.
     *
     * @param name The name of the celestial body
     * @param type The type of celestial body (Star, Planet, or Moon)
     * @param sensorData The sensor data containing environmental measurements
     */
    public Node(String name, String type, SensorData sensorData) {
        this.name = name;
        this.type = type;
        this.sensorData = sensorData;
        this.children = new ArrayList<>();
    }
    
    /**
     * Gets the name of the celestial body.
     *
     * @return The name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the celestial body.
     *
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the celestial body.
     *
     * @return The type of the node (Star, Planet, or Moon)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the celestial body.
     *
     * @param type The new type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets the sensor data associated with this celestial body.
     *
     * @return The SensorData object containing environmental measurements
     */
    public SensorData getSensorData(){
        return sensorData;
    }

    /**
     * Sets the sensor data for this celestial body.
     *
     * @param sensorData The new SensorData to set
     */
    public void setSensorData(SensorData sensorData){
        this.sensorData = sensorData;
    }

    /**
     * Gets the list of child nodes (celestial bodies orbiting this one).
     *
     * @return List of child Node objects
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Sets the list of child nodes.
     *
     * @param children The new list of child nodes to set
     */
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * Adds a child node to this node's children list.
     *
     * @param child The Node to add as a child
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Removes a child node from this node's children list.
     *
     * @param child The Node to remove from children
     */
    public void removeChild(Node child) {
        children.remove(child);
    }
}