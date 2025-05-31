package src.HWSystem.Protocols; 
public class I2C implements Protocol{
    @Override
    public String read()
    {
        return getProtocolName() + ": Reading.";
    }

    @Override
    public void write(String data) 
    {
        System.out.printf("%s: Writing \"%s\".\n", getProtocolName(), data);
    }

    @Override
    public String getProtocolName()
    {
        return "I2C";
    }
}
