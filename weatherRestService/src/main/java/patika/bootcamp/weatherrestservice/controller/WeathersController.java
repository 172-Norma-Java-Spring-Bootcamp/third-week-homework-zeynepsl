package patika.bootcamp.weatherrestservice.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import patika.bootcamp.weatherrestservice.model.CurrentWeather;
import patika.bootcamp.weatherrestservice.model.WeatherForecast;
import patika.bootcamp.weatherrestservice.service.WeatherService;

@RestController
@RequestMapping(path =  "/api/weathers/")
@Validated
public class WeathersController {
	
	@Autowired
	WeatherService weatherService;
	
	@GetMapping("getCurrentWeather")
	public CurrentWeather getCurrentWeather(
			@RequestParam @Length(min = 2, max = 10, message = "sehir min 2 karakter olmali") String city,
			@RequestParam @Length(min = 2, max = 10, message = "ulke min 2 karakter olmali") String country){
	 	return weatherService.getCurrentWeather(city, country);
	}
	
	//verilen sehir ve ulke bilgisine gore anlik hava durumu bilgisini getirir:
	@GetMapping("getCurrentWeatherWithOtoUrl")
	public CurrentWeather getCurrentWeatherWithOtoUrl(
			@RequestParam @Length(min = 2, max = 10, message = "must be at least 2 characters") String city, 
			@RequestParam @Length(min = 2, max = 10, message = "must be at least 2 characters") String country){
		return weatherService.getCurrentWeatherWithOtoUrl(city, country);
	}
	
	//verilen sehir ve ulke bilgisine gore hava durumu tahminlerini getirir:
	@GetMapping("getWeatherForecastWithOtoUrl")
	public WeatherForecast getWeatherForecastWithOtoUrl(
			@RequestParam @Length(min = 2, max = 10, message = "must be at least 2 characters") String city, 
			@RequestParam @Length(min = 2, max = 10, message = "must be at least 2 characters") String country) {
		return weatherService.getWeatherForecastWithOtoUrl(city, country);
	}
	
	@PutMapping(path =  "search/{weatherId}")
	public ResponseEntity<Object> update(@PathVariable @Positive(message = "0 dan kucuk olamaz") int weatherId){
		return ResponseEntity.ok().build();	
	}
	
	@PostMapping(path = "add")
	public ResponseEntity<Object> addWeather(@RequestBody @Valid CurrentWeather weather){
		return ResponseEntity.ok(weather.getDescription());
	}
	
	//istek parametrelerini validasyona uygun gondermezsen metodun icine bile girmez
	@GetMapping(path =  "getWeathers")
	public ResponseEntity<Object> getWeathers(@RequestParam("id") int id, @RequestParam("info") String info) throws Exception{
		//NullPointerException geldigi anda spring araya girer, spring in yapısı proxy objeler üzerinden
		//tüm objeler proxy oldugu icin istedigi noktada istedigi seyi yapar
		//proxy design pattern spring in temeli

		//boyle bir kod yazılımyor bu ornek:
		//bu kod başka sınıflardan başka katmanlardan gelecek --> burada başka servisi çağıracak--> weatherServise.add(weather) vs.
		throw new NullPointerException();//spring buradaki exception ı handle edip RestServiceControllerAdvice a gönderiyor
	}

	
}
