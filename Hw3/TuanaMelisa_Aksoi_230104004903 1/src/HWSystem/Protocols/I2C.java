package src.HWSystem.Protocols;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/** 
 * I2C protocol implementation 
 */
public class I2C implements Protocol {
    private Stack<String> logs = new Stack<>(); 
    private String logName;
    private boolean isOpen;

    /**
     * Creates a new I2C instance for the specified port.
     * @param portID The port number to use
     */
    public I2C(int portID) {
        this.logName = "I2C_" + portID + ".log"; 
        logs.push("Port Opened."); 
        isOpen = true;
    }

    /**
     * Returns the protocol name.
     * @return The string "I2C"
     */
    @Override
    public String getProtocolName() {
        return "I2C";
    }

    /**
     * Reads data from the I2C bus.
     * @return Empty string
     */
    @Override
    public String read() {
        logs.push("Reading.");
        return "";
    }

    /**
     * Writes data to the I2C bus.
     * @param data The data to write
     */
    @Override
    public void write(String data) {
        logs.push("Writing \"" + data + "\".");
    }

    /**
     * Closes the I2C connection and saves logs.
     */
    @Override
    public void close() {
        if (!isOpen) return; 

        try (FileWriter writer = new FileWriter(logName)) {
            while (logs.size() > 0) {
                writer.write(logs.pop() + "\n"); 
            }
        } catch (IOException e) {
            System.err.println("Error writing log file: " + logName);
        }

        isOpen = false; 
    }
}
