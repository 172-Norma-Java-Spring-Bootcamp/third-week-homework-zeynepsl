package patika.bootcamp.weatherrestservice.service;

import org.springframework.http.ResponseEntity;

import patika.bootcamp.weatherrestservice.model.CurrentWeather;

public interface WeatherService {
	CurrentWeather getCurrentWeather(String city, String country);
    
    CurrentWeather getCurrentWeatherStub(String city, String country);
	
    CurrentWeather convert(ResponseEntity<String> response);
    
}
