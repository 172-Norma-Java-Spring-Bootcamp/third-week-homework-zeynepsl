package patika.bootcamp.weatherrestservice.model;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherForecast {
	List<CurrentWeather> weathers;
	
	public WeatherForecast() {
		weathers = new ArrayList<CurrentWeather>();
	}
}
