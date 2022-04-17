package patika.bootcamp.weatherrestservice.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import patika.bootcamp.weatherrestservice.model.CurrentWeather;
import patika.bootcamp.weatherrestservice.model.WeatherForecast;

@Service
public class LiveWeatherServiceImpl implements WeatherService {
	private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}&units=metric";
	private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q={city},{country}&APPID={key}&units=metric";
	private static String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=%s,%s&APPID=%s&units=metric";
	
	private final String apiKey = "32b956a6ca1086c0de29d387f53565cd";
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public LiveWeatherServiceImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
		this.restTemplate = restTemplateBuilder.build();
		this.objectMapper = objectMapper;
	}
	
	public CurrentWeather getCurrentWeatherStub(String city, String country) {
		return new CurrentWeather("Clear", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
	}

	public CurrentWeather getCurrentWeather(String city, String country) {
		HttpHeaders headers = new HttpHeaders();//request headerlarini set etmek icin HttpHeaders kullaniyoruz
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));	
		HttpEntity<String> entity = new HttpEntity<String>(headers);//request nesnesini sarmalamak (warp) icin HttpEntity kullaniyoruz
		
		//String strUri = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + apiKey + "&units=imperial";
		String strUri = String.format(baseUrl, city, country, apiKey);
		
		ResponseEntity<String> res = restTemplate.exchange(strUri, HttpMethod.GET, entity, String.class);
		//exchange -->> url, http method, , donus tipi
		return convertToCurrentWeather(res);
	}
	
	//url yi restTemplate ile otomatik olusturmak icin:
	
	public CurrentWeather getCurrentWeatherWithOtoUrl(String city, String country){
		URI url = new UriTemplate(WEATHER_URL).expand(city, country, apiKey);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertToCurrentWeather(response);
	}
	
	public WeatherForecast getWeatherForecastWithOtoUrl(String city, String country){
		URI url = new UriTemplate(FORECAST_URL).expand(city, country, apiKey);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertToWeatherForecast(response);
	}


	/*
	 * OpenWeatherMap API'ye bir REST cagrisi yapiyoruz --> getCurrentWeatherWithOtoUrl() ve
	 * ardindan CurrentWeather POJO'muza, verilen yaniti cevirebilmek için
	 * Jackson'in ObjectMapper'ini kullaniyoruz
	 */
	public CurrentWeather convertToCurrentWeather(ResponseEntity<String> response) {
		try {
			JsonNode root = objectMapper.readTree(response.getBody());
			return new CurrentWeather(root.path("weather").get(0).path("main").asText(),
					BigDecimal.valueOf(root.path("main").path("temp").asDouble()),
					BigDecimal.valueOf(root.path("main").path("feels_like").asDouble()),
					BigDecimal.valueOf(root.path("wind").path("speed").asDouble()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error parsing JSON", e);
		}
	}
	
	public WeatherForecast convertToWeatherForecast(ResponseEntity<String> response) {
		WeatherForecast forecast = new WeatherForecast();
		List<CurrentWeather> currentWeathers = new ArrayList<CurrentWeather>();
	
		try {
			JsonNode root = objectMapper.readTree(response.getBody());
			for(int i = 0; i < 30; i++) {
				CurrentWeather weather = new CurrentWeather();
				String description = root.path("list").get(i).path("weather").get(0).path("main").asText();
				BigDecimal temp = BigDecimal.valueOf(root.path("list").get(i).path("main").path("temp").asDouble());
				BigDecimal feelsLike = BigDecimal.valueOf(root.path("list").get(i).path("main").path("feels_like").asDouble());
				BigDecimal windSpeed = BigDecimal.valueOf(root.path("list").get(i).path("wind").path("speed").asDouble());
				weather.setDescription(description);
				weather.setTemperature(temp);
				weather.setFeelsLike(feelsLike);
				weather.setWindSpeed(windSpeed);
				currentWeathers.add(weather);
			}
			
			forecast.setWeathers(currentWeathers);
			return forecast;
		} 
		catch (JsonProcessingException e) {
			throw new RuntimeException("Error parsing JSON", e);
		}
	}
	

}
