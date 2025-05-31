package PlanetSystem;

/**
 * Represents sensor data collected from a celestial body.
 * Contains environmental measurements including temperature, pressure, humidity, and radiation levels.
 */
public class SensorData{
    private double temperature;
    private double pressure;
    private double humidity;
    private double radiation;

    /**
     * Creates a new SensorData object with specified measurements.
     *
     * @param temperature The temperature in Kelvin
     * @param pressure The pressure in Pascals
     * @param humidity The humidity percentage (0-100)
     * @param radiation The radiation level in Sieverts
     */
    public SensorData(double temperature, double pressure, double humidity, double radiation) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.radiation = radiation;
    }

    /**
     * Gets the temperature measurement.
     *
     * @return The temperature in Kelvin
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Sets the temperature measurement.
     *
     * @param temperature The new temperature in Kelvin
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Sets the pressure measurement.
     *
     * @param pressure The new pressure in Pascals
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Gets the pressure measurement.
     *
     * @return The pressure in Pascals
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Gets the humidity measurement.
     *
     * @return The humidity percentage (0-100)
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Sets the humidity measurement.
     *
     * @param humidity The new humidity percentage (0-100)
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets the radiation measurement.
     *
     * @return The radiation level in Sieverts
     */
    public double getRadiation() {
        return radiation;
    }

    /**
     * Sets the radiation measurement.
     *
     * @param radiation The new radiation level in Sieverts
     */
    public void setRadiation(double radiation) {
        this.radiation = radiation;
    } 
}