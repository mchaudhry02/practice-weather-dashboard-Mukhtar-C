package com.launchcode.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WeatherDashboard {

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENWEATHER_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("ERROR: No API key found.");
            System.out.println("Set the OPENWEATHER_API_KEY environment variable before running this app.");
            System.out.println("See README.md for instructions on getting a free API key.");
            return;
        }

        WeatherService weatherService = new WeatherService(apiKey);
        Scanner scanner = new Scanner(System.in);
        List<String> cities = new ArrayList<>();

        System.out.println("=====================================");
        System.out.println(" Welcome to your Morning Weather Dashboard");
        System.out.println("=====================================");

        setUpCities(scanner, cities);
        runMenu(scanner, cities, weatherService);

        System.out.println("Have a great day!");
        scanner.close();
    }

  
    private static void setUpCities(Scanner scanner, List<String> cities) {
        System.out.println("\nLet's set up the cities you want to check each morning.");
        System.out.println("Enter between 2 and 3 city names (e.g. \"St. Louis\" or \"London,GB\").\n");

        while (cities.size() < 3) {
            System.out.print("Enter city #" + (cities.size() + 1)
                    + (cities.size() >= 2 ? " (or press Enter to stop): " : ": "));
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                if (cities.size() >= 2) {
                    break;
                }
                System.out.println("Please enter at least 2 cities.");
                continue;
            }

            cities.add(input);
        }
    }

  
    private static void runMenu(Scanner scanner, List<String> cities, WeatherService weatherService) {
        boolean running = true;

        while (running) {
            printMenu(cities);
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            int option;
            try {
                option = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.\n");
                continue;
            }

            if (option == cities.size() + 1) {
                running = false;
            } else if (option >= 1 && option <= cities.size()) {
                String city = cities.get(option - 1);
                showWeather(weatherService, city);
            } else {
                System.out.println("That's not a valid option. Try again.\n");
            }
        }
    }

    private static void printMenu(List<String> cities) {
        System.out.println("\n---------- Menu ----------");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". Check weather in " + cities.get(i));
        }
        System.out.println((cities.size() + 1) + ". Quit");
        System.out.println("---------------------------");
    }

    private static void showWeather(WeatherService weatherService, String city) {
        System.out.println("\nFetching weather for " + city + "...");
        try {
            WeatherReport report = weatherService.getCurrentWeather(city);
            System.out.println(report.toDashboardString());
        } catch (WeatherApiException e) {
            System.out.println("Oops! " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching the weather: " + e.getMessage());
        }
    }
}