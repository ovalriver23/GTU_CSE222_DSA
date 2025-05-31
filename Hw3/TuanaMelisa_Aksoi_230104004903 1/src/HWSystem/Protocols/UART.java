package src.HWSystem.Protocols;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/** 
 * UART protocol implementation 
 */
public class UART implements Protocol{
    private Stack<String> logs = new Stack<>();
    private String logName;
    private boolean isOpen;

    /**
     * Creates a new UART instance for the specified port.
     * @param portID The port number to use
     */
    public UART(int portID) {
        this.logName = "UART_" + portID + ".log"; 
        logs.push("Port Opened."); 
        isOpen = true;
    }

    /**
     * Returns the protocol name.
     * @return The string "UART"
     */
    @Override
    public String getProtocolName()
    {
        return "UART";
    }

    /**
     * Reads data from the UART bus.
     * @return Empty string
     */
    @Override
    public String read()
    {
        logs.push("Reading.");
        return "";
    }

    /**
     * Writes data to the UART bus.
     * @param data The data to write
     */
    @Override
    public void write(String data) 
    {
        logs.push("Writing \"" + data + "\".");
    }

    /**
     * Closes the UART connection and saves logs.
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
