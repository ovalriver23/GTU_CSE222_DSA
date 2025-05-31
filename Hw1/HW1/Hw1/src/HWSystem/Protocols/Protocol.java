package src.HWSystem.Protocols;

public interface Protocol{
    String read();
    String getProtocolName();
    void write(String data);
}