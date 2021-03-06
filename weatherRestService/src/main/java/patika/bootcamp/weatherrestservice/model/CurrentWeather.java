package patika.bootcamp.weatherrestservice.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*CurrentWeather POJO muz burada
 *free API den gelen bilgiyi ayristirip bu classdaki degiskenlere atayacagiz
 *boylelikle client'a daha anlasilir bir response donmus olacagiz*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeather {

	@NotBlank(message = "bos olamaz info")
	private String description;

	@Min(value = -10, message = "-10 dan kucuk olamaz")
	private BigDecimal temperature;
	private BigDecimal feelsLike;
	private BigDecimal windSpeed;

	public String toString() {
		return "Weather{" + "info: " + description + 
				"derece: " + temperature + 
				"hissedilen sicaklik: " + feelsLike + 
				"ruzgar hizi: " + windSpeed + "}";
	}
}
