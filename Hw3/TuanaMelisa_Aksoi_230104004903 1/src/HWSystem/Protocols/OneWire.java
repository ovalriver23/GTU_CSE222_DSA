package src.HWSystem.Protocols;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/** 
 * OneWire protocol implementation 
 */
public class OneWire implements Protocol{
    private Stack<String> logs = new Stack<>();
    private String logName;
    private boolean isOpen;

    /**
     * Creates a new OneWire instance for the specified port.
     * @param portID The port number to use
     */
    public OneWire(int portID) {
        this.logName = "OneWire_" + portID + ".log"; 
        logs.push("Port Opened."); 
        isOpen = true;
    }

    /**
     * Returns the protocol name.
     * @return The string "One Wire"
     */
    @Override
    public String getProtocolName()
    {
        return "One Wire";
    }

    /**
     * Reads data from the OneWire bus.
     * @return Empty string
     */
    @Override
    public String read()
    {
        logs.push("Reading.");
        return ""; 
    }

    /**
     * Writes data to the OneWire bus.
     * @param data The data to write
     */
    @Override
    public void write(String data) 
    {
        logs.push("Writing \"" + data + "\".");
    }

    /**
     * Closes the OneWire connection and saves logs.
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


