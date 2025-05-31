package PlanetSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Manages a hierarchical planetary system containing stars, planets, and satellites.
 * Provides functionality to create and modify the system, and generate various reports.
 */
public class PlanetSystem {
    private Node root;

    /**
     * Creates a new empty planetary system.
     */
    public PlanetSystem() {
        root = null;
    }

    /**
     * Creates a new planetary system with a star as its root.
     *
     * @param starName The name of the star
     * @param temperature The star's temperature in Kelvin
     * @param pressure The star's pressure in Pascals
     * @param humidity The star's humidity percentage (must be 0 for stars)
     * @param radiation The star's radiation level in Sieverts
     */
    public void createPlanetSystem(String starName, double temperature, double pressure, double humidity, double radiation) {
        if (humidity != 0) {
            System.err.println("ERROR: Star must have 0% humidity.");
            return;
        }

        SensorData starData = new SensorData(temperature, pressure, humidity, radiation);
        root = new Node(starName, "Star", starData);
        System.out.println("Created planetary system with star: '" + starName + "'.");
    }

    /**
     * Finds a node in the system by its name using depth-first search.
     *
     * @param current The current node being examined
     * @param name The name of the node to find
     * @return The found node, or null if not found
     */
    private Node findParent(Node current, String name) {
        if (current == null) {
            return null;
        }
        
        if (current.getName().equals(name)) {
            return current;
        }
        
        List<Node> children = current.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            Node result = findParent(child, name);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }

    /**
     * Adds a new planet to the system under a specified parent.
     *
     * @param planetName The name of the new planet
     * @param parentName The name of the parent celestial body
     * @param temperature The planet's temperature in Kelvin
     * @param pressure The planet's pressure in Pascals
     * @param humidity The planet's humidity percentage (0-100)
     * @param radiation The planet's radiation level in Sieverts
     */
    public void addPlanet(String planetName, String parentName, double temperature, double pressure, double humidity, double radiation){
        if (root == null) {
            System.err.println("ERROR: Planetary system does not exist.");
            return;
        }

        if (humidity < 0 || humidity > 100) {
            System.err.println("ERROR: Humidity must be between 0 and 100.");
            return;
        }

        Node parent = findParent(root, parentName);
        if (parent == null) {
            System.err.println("ERROR: Parent '" + parentName + "' not found in the planetary system.");
            return;
        }

        if (parent.getType().equals("Moon")) {
            System.err.println("ERROR: Cannot add a planet to a moon.");
            return;
        }
        
        for (Node child : parent.getChildren()) {
            if (child.getName().equals(planetName)) {
                System.err.println("ERROR: Planet with same name already exists under this parent.");
                return;
            }
        }

        SensorData planetData = new SensorData(temperature, pressure, humidity, radiation);
        Node newPlanet = new Node(planetName, "Planet", planetData);
        parent.addChild(newPlanet);
        System.out.println("Added planet '" + planetName + "' under '" + parentName + "'.");
    }
    
    /**
     * Adds a new satellite (moon) to the system under a specified parent.
     *
     * @param satelliteName The name of the new satellite
     * @param parentName The name of the parent celestial body
     * @param temperature The satellite's temperature in Kelvin
     * @param pressure The satellite's pressure in Pascals
     * @param humidity The satellite's humidity percentage (0-100)
     * @param radiation The satellite's radiation level in Sieverts
     */
    public void addSatellite(String satelliteName, String parentName, double temperature, double pressure, double humidity, double radiation) {
        if (root == null) {
            System.err.println("ERROR: Planetary system does not exist.");
            return;
        }

        if (humidity < 0 || humidity > 100) {
            System.err.println("ERROR: Humidity must be between 0 and 100.");
            return;
        }

        Node parent = findParent(root, parentName);
        if (parent == null) {
            System.err.println("ERROR: Parent '" + parentName + "' not found in the planetary system.");
            return;
        }
    
        if (parent.getType().equals("Moon")) {
            System.err.println("ERROR: Cannot add satellite to another satellite '" + parentName + "'.");
            return;
        }
    
        for (Node child : parent.getChildren()) {
            if (child.getName().equals(satelliteName)) {
                System.err.println("ERROR: Satellite with same name already exists under this parent.");
                return;
            }
        }
    
        SensorData satelliteData = new SensorData(temperature, pressure, humidity, radiation);
        Node newSatellite = new Node(satelliteName, "Moon", satelliteData);
        parent.addChild(newSatellite);
        System.out.println("Added satellite '" + satelliteName + "' under '" + parentName + "'.");
    }

    /**
     * Finds all celestial bodies with radiation levels above the specified threshold.
     *
     * @param threshold The radiation threshold in Sieverts
     */
    public void findRadiationAnomalies(double threshold) {
        if (root == null) {
            System.err.println("ERROR: Planetary system does not exist.");
            return;
        }
        
        if (threshold < 0) {
            System.err.println("ERROR: Radiation threshold cannot be negative.");
            return;
        }
    
        List<Node> result = new ArrayList<>();
        findAnomalies(root, threshold, result);
        
        for (Node node : result) {
            System.out.printf("%s: %.2f Sieverts%n", 
                             node.getName(), 
                             node.getSensorData().getRadiation());
        }
    }
    
    /**
     * Helper method to recursively find radiation anomalies.
     *
     * @param current The current node being examined
     * @param threshold The radiation threshold in Sieverts
     * @param result List to store nodes with radiation above threshold
     */
    private void findAnomalies(Node current, double threshold, List<Node> result) {
        if (current == null) return;
        
        if (current.getSensorData().getRadiation() > threshold) {
            result.add(current);
        }
        
        for (Node child : current.getChildren()) {
            findAnomalies(child, threshold, result);
        }
    }

    /**
     * Finds and displays the path from the root to a specified celestial body.
     *
     * @param targetName The name of the celestial body to find
     */
    public void getPathTo(String targetName) {
        if (root == null) {
            System.err.println("ERROR: Planetary system does not exist.");
            return;
        }
        
        Stack<String> path = new Stack<>();
        boolean found = findPath(root, targetName, path);
        
        if (!found) {
            System.err.println("ERROR: The name is not found in the system");
            return;
        }
        
        String[] pathArray = path.toArray(new String[0]);
        
        for (int i = 0; i < pathArray.length; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("   ");
            }
            System.out.println("└── " + pathArray[i]);
        }
    }
    
    /**
     * Helper method to find the path to a node using depth-first search.
     *
     * @param current The current node being examined
     * @param targetName The name of the target node
     * @param path Stack to store the path
     * @return true if the target was found, false otherwise
     */
    private boolean findPath(Node current, String targetName, Stack<String> path) {
        path.push(current.getName());
        
        if (current.getName().equals(targetName)) {
            return true;
        }
        
        for (Node child : current.getChildren()) {
            if (findPath(child, targetName, path)) {
                return true;
            }
        }
        
        path.pop();
        return false;
    }

    /**
     * Prints a complete mission report for the entire planetary system.
     */
    public void printMissionReport() {
        if (root == null) {
            System.err.println("ERROR: Planetary system not initialized.");
            return;
        }

        printRecursive(root, 0);
    }
    
    /**
     * Helper method to recursively print the mission report.
     *
     * @param current The current node being printed
     * @param depth The current depth in the tree
     */
    private void printRecursive(Node current, int depth) {
        if (current == null) return;
    
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    
        SensorData data = current.getSensorData();
        System.out.printf(
            "%s (%s): %.2f Kelvin, %.4f Pascal, %.2f%% humidity, %.2f Sievert%n",
            current.getName(), 
            current.getType(), 
            data.getTemperature(),
            data.getPressure(),
            data.getHumidity(),
            data.getRadiation()
        );
    
        List<Node> children = current.getChildren();
        for (int i = 0; i < children.size(); i++) {
            printRecursive(children.get(i), depth + 1);
        }
    }

    /**
     * Prints a mission report for a specific celestial body.
     *
     * @param nodeName The name of the celestial body to report on
     */
    public void printMissionReport(String nodeName) {
        if (root == null) {
            System.err.println("ERROR: Planetary system does not exist.");
            return;
        }
        
        Node targetNode = findNodeDFS(root, nodeName);
        
        if (targetNode == null) {
            System.err.println("ERROR: Celestial body '" + nodeName + "' not found in the system.");
            return;
        }
        
        System.out.println("\n===== MISSION REPORT FOR " + nodeName.toUpperCase() + " =====");
        printNodeData(targetNode);
        System.out.println("=========================================");
    }
    
    /**
     * Helper method to print data for a specific node.
     *
     * @param node The node whose data to print
     */
    private void printNodeData(Node node) {
        SensorData data = node.getSensorData();
        
        String nodeType = node.getType();
        System.out.println(node.getName() + " (" + nodeType + ")");
        System.out.println("Temperature: " + data.getTemperature() + " Kelvin");
        System.out.println("Pressure: " + data.getPressure() + " Pascals");
        System.out.println("Humidity: " + data.getHumidity() + "%");
        System.out.println("Radiation: " + data.getRadiation() + " Sieverts");
    }
    
    /**
     * Helper method to find a node by name using depth-first search.
     *
     * @param current The current node being examined
     * @param nodeName The name of the node to find
     * @return The found node, or null if not found
     */
    private Node findNodeDFS(Node current, String nodeName) {
        if (current.getName().equals(nodeName)) {
            return current;
        }
        
        for (Node child : current.getChildren()) {
            Node result = findNodeDFS(child, nodeName);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }
}
