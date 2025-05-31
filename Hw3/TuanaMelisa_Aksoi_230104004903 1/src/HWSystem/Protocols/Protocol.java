package src.HWSystem.Protocols;

/**
 * The Protocol interface defines the contract for communication protocols.
 * Implementations of this interface provide methods for reading from and writing to 
 * a communication channel, as well as methods for retrieving the protocol name and 
 * closing the connection.
 */
public interface Protocol{
    String read();
    String getProtocolName();
    void write(String data);
    void close();
}