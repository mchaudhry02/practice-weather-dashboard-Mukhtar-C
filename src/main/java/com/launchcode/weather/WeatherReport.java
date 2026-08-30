package com.launchcode.weather;


public class WeatherReport {

    private final String city;
    private final String country;
    private final double temperature;
    private final double feelsLike;
    private final int humidity;
    private final String description;
    private final double windSpeed;

    public WeatherReport(String city, String country, double temperature, double feelsLike,
                          int humidity, String description, double windSpeed) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.description = description;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    
    public String toDashboardString() {
        String location = country == null || country.isEmpty() ? city : city + ", " + country;

        return """
                ------------------------------------
                 Weather Dashboard: %s
                ------------------------------------
                 Conditions:   %s
                 Temperature:  %.1f\u00B0F (feels like %.1f\u00B0F)
                 Humidity:     %d%%
                 Wind Speed:   %.1f mph
                ------------------------------------
                """.formatted(location, capitalize(description), temperature, feelsLike, humidity, windSpeed);
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}