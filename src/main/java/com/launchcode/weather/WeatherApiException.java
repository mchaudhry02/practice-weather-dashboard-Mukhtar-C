package com.launchcode.weather;


public class WeatherApiException extends Exception {
    public WeatherApiException(String message) {
        super(message);
    }
}