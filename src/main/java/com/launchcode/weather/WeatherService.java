package com.launchcode.weather;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class WeatherService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final String apiKey;
    private final HttpClient httpClient;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Fetches current weather for the given city and returns it as a WeatherReport.
     *
     * @param city the city name, e.g. "St. Louis" or "London,GB"
     * @return a populated WeatherReport
     * @throws IOException          if the request fails or the response can't be read
     * @throws InterruptedException if the HTTP call is interrupted
     * @throws WeatherApiException  if the API returns a non-200 response (bad city, bad key, etc.)
     */
    public WeatherReport getCurrentWeather(String city) throws IOException, InterruptedException, WeatherApiException {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = BASE_URL + "?q=" + encodedCity + "&appid=" + apiKey + "&units=imperial";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            String message = extractErrorMessage(response.body());
            throw new WeatherApiException("Could not fetch weather for \"" + city + "\" (HTTP "
                    + response.statusCode() + "): " + message);
        }

        return parseWeatherReport(response.body());
    }

    private String extractErrorMessage(String body) {
        try {
            JSONObject json = new JSONObject(body);
            return json.optString("message", "Unknown error");
        } catch (Exception e) {
            return "Unknown error";
        }
    }

    private WeatherReport parseWeatherReport(String json) {
        JSONObject root = new JSONObject(json);

        String cityName = root.getString("name");
        String country = root.getJSONArray("weather").isEmpty()
                ? ""
                : root.optJSONObject("sys") != null ? root.getJSONObject("sys").optString("country", "") : "";

        JSONObject main = root.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");

        JSONObject weatherDetail = root.getJSONArray("weather").getJSONObject(0);
        String description = weatherDetail.getString("description");

        double windSpeed = root.has("wind") ? root.getJSONObject("wind").optDouble("speed", 0) : 0;

        return new WeatherReport(cityName, country, temperature, feelsLike, humidity, description, windSpeed);
    }
}