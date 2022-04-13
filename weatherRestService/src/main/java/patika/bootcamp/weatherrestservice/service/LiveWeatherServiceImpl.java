package patika.bootcamp.weatherrestservice.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import patika.bootcamp.weatherrestservice.model.CurrentWeather;

@Service
public class LiveWeatherServiceImpl implements WeatherService{
	private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";
	private final String apiKey = "32b956a6ca1086c0de29d387f53565cd";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LiveWeatherServiceImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }
   
    
    public CurrentWeather getCurrentWeather(String city, String country) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	 String strUri= "http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+apiKey+"&units=imperial";
    	 ResponseEntity<String> res = restTemplate.exchange(strUri, HttpMethod.GET, entity, String.class);
    	 return convert(res);
    }
    
    public CurrentWeather getCurrentWeatherStub(String city, String country) {
        return new CurrentWeather("Clear", BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);
	}  
    
    /* OpenWeatherMap API'ye bir REST cagrisi yapiyoruz --> getCurrentWeather()
     * ve ardindan CurrentWeather POJO'muza, verilen yaniti cevirebilmek için Jackson'ın ObjectMapper'ını kullanıyoruz
     */
    public CurrentWeather convert(ResponseEntity<String> response) {
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
}
