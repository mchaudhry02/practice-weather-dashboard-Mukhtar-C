# Weather Dashboard

A Java console application for a "morning routine" app. It fetches current
weather conditions from the [OpenWeatherMap API](https://openweathermap.org/api)
for 2–3 cities you choose, and displays a simple, friendly dashboard for each.

## Features

- **Current Weather** – fetches live conditions for a specific city
- **Weather Summary** – shows temperature, description, and humidity (plus feels-like temp and wind speed)
- **Multiple Cities** – track 2–3 cities in one session
- **Simple Menu** – console menu to pick which city to check, or quit

## Project Structure

```
weather-dashboard/
├── pom.xml
├── README.md
└── src/main/java/com/launchcode/weather/
    ├── WeatherDashboard.java   # main() and console menu
    ├── WeatherService.java     # talks to the OpenWeatherMap API
    ├── WeatherReport.java      # holds/formats the weather data
    └── WeatherApiException.java
```

## Setup

### 1. Get a free API key

1. Sign up at [openweathermap.org/api](https://openweathermap.org/api).
2. Once logged in, go to your account's **API keys** tab and copy your key.
3. New keys can take up to a couple of hours to activate — if you get a
   `401 Unauthorized` error right away, wait a bit and try again.

### 2. Set the API key as an environment variable

The app reads your key from an environment variable called
`OPENWEATHER_API_KEY` so it never gets hardcoded or committed to GitHub.

**macOS / Linux (bash/zsh):**
```bash
export OPENWEATHER_API_KEY="your_key_here"
```

**Windows (PowerShell):**
```powershell
$env:OPENWEATHER_API_KEY = "your_key_here"
```

(Add it to your shell profile, e.g. `~/.zshrc` or `~/.bash_profile`, if you
want it to persist across terminal sessions.)

### 3. Build and run

With Maven installed:

```bash
mvn compile exec:java
```

Or build a runnable jar:

```bash
mvn package
java -jar target/weather-dashboard.jar
```

## Example Session

```
=====================================
 Welcome to your Morning Weather Dashboard
=====================================

Let's set up the cities you want to check each morning.
Enter between 2 and 3 city names (e.g. "St. Louis" or "London,GB").

Enter city #1: St. Louis
Enter city #2: London,GB
Enter city #3 (or press Enter to stop):

---------- Menu ----------
1. Check weather in St. Louis
2. Check weather in London,GB
3. Quit
---------------------------
Choose an option: 1

Fetching weather for St. Louis...
------------------------------------
 Weather Dashboard: St. Louis, US
------------------------------------
 Conditions:   Clear sky
 Temperature:  78.4°F (feels like 79.1°F)
 Humidity:     52%
 Wind Speed:   6.9 mph
------------------------------------
```

## Notes

- City names can include a country code for accuracy, e.g. `"Paris,FR"`
  vs. `"Paris,US"` (Paris, Texas).
- Temperatures are shown in Fahrenheit (`units=imperial` in the API call) —
  change this in `WeatherService.java` if you'd prefer Celsius (`units=metric`).