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
		HttpHeaders headers = new HttpHeaders();//we use HttpHeaders for setting request headers
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));	
		HttpEntity<String> entity = new HttpEntity<String>(headers);//we use HttpEntity for wrapping request object
		
		//String strUri = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + apiKey + "&units=imperial";
		String strUri = String.format(baseUrl, city, country, apiKey);
		
		ResponseEntity<String> res = restTemplate.exchange(strUri, HttpMethod.GET, entity, String.class);
		//exchange -->> url, http method, , return type
		return convertCurrentWeather(res);
	}
	
	//for creating automatically the url with restTemplate
	
	public CurrentWeather getCurrentWeatherWithOtoUrl(String city, String country){
		URI url = new UriTemplate(WEATHER_URL).expand(city, country, apiKey);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertCurrentWeather(response);
	}
	
	public WeatherForecast getWeatherForecastWithOtoUrl(String city, String country){
		URI url = new UriTemplate(FORECAST_URL).expand(city, country, apiKey);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convertWeatherForecast(response);
	}


	/*
	we call OpenWeatherMap API with getCurrentWeatherWithOtoUrl() method
	after then,
	we use ObjectMapper of json for mapping the response to our CurrentWeather POJO
	 */
	public CurrentWeather convertCurrentWeather(ResponseEntity<String> response) {
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
	
	public WeatherForecast convertWeatherForecast(ResponseEntity<String> response) {
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
