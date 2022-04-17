package patika.bootcamp.weatherrestservice.model;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherForecast {
	private Collection<CurrentWeather> weathers;
	
	public WeatherForecast() {
		weathers = new ArrayList<CurrentWeather>();
	}
}
