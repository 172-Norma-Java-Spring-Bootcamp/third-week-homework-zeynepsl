package patika.bootcamp.weatherrestservice.service;

import org.springframework.http.ResponseEntity;

import patika.bootcamp.weatherrestservice.model.CurrentWeather;
import patika.bootcamp.weatherrestservice.model.WeatherForecast;

public interface WeatherService {
	CurrentWeather getCurrentWeather(String city, String country);
   
	CurrentWeather getCurrentWeatherWithOtoUrl(String city, String country); 
	
	WeatherForecast getWeatherForecastWithOtoUrl(String city, String country);
	
    CurrentWeather getCurrentWeatherStub(String city, String country);
	
    CurrentWeather convertToCurrentWeather(ResponseEntity<String> response);
    
    WeatherForecast convertToWeatherForecast(ResponseEntity<String> response);
   
}
