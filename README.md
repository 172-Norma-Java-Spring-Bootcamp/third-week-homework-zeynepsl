# Week3-Homework

**Weather API**

Merhaba arkadaşlar, bu hafta bir hava durumu API'sı yapalım. Bunun için internette bir sürü Free API'lar var. Aşağıda bazı API websitelerini ekledim. Dileyen başka API'larda kullanabilir.

* https://openweathermap.org/api
* https://www.weatherapi.com/
* https://weatherstack.com/


Temel amacımız, bu API'lar la haberleşecek bir API yazmak. Kullanıcıdan alacağımız, ülke / şehir bilgisine göre günlük, haftalık ve aylık hava durumu raporunu gösterelim.

İstek önce sizin yazacağınız API'ye gelecek, sonrasnda siz isteği aldıktan sonra Free Weather API'lara istek atıp bu bilgileri tekrar geri döneceksiniz.

Bu işlem için size anlatmadığım ama kullanımı oldukça kolay **RestTemplate** sınıfını kullanabilirsiniz. 

Sizden beklentim, doğru bir API strcuture çıkartmanız. 
Model veya parametrelerinizde validasyonlar yapmanız.
Exception handling işlemlerini halletmeniz. 

Ödevin teslim bitiş tarihi Cumartesi 13:00'a kadardır. 

# week3-homework-zeynepsl

## request 1 - get current weather by given city and country
#### code
[code link](https://github.com/172-Norma-Java-Spring-Bootcamp/third-week-homework-zeynepsl/blob/70e2ea09197222cf1e6b7750e845d28abb3c53b5/weatherRestService/src/main/java/patika/bootcamp/weatherrestservice/service/LiveWeatherServiceImpl.java#L60)
``` java
public CurrentWeather getCurrentWeatherWithOtoUrl(String city, String country);
```
#### endpoint
```
http://localhost:8080/api/weathers/getCurrentWeatherWithOtoUrl?city=ankara&country=turkey
```
#### response 
``` xml
{
    "description": "Clear",
    "temperature": 13.24,
    "feelsLike": 11.69,
    "windSpeed": 4.12
}
```

## request 2 - get weather forecast by given city and country
#### code
[code link](https://github.com/172-Norma-Java-Spring-Bootcamp/third-week-homework-zeynepsl/blob/70e2ea09197222cf1e6b7750e845d28abb3c53b5/weatherRestService/src/main/java/patika/bootcamp/weatherrestservice/service/LiveWeatherServiceImpl.java#L66)
```java
public WeatherForecast getWeatherForecastWithOtoUrl(String city, String country);
```
#### endpoint
```
http://localhost:8080/api/weathers/getWeatherForecastWithOtoUrl?city=ankara&country=turkey
```
#### response
```xml
{
    "weathers": [
        {
            "description": "Clear",
            "temperature": 12.03,
            "feelsLike": 10.52,
            "windSpeed": 1.43
        },
        {
            "description": "Clear",
            "temperature": 9.88,
            "feelsLike": 9.88,
            "windSpeed": 1.15
        },
        {
            "description": "Clear",
            "temperature": 7.16,
            "feelsLike": 7.16,
            "windSpeed": 1.29
        },
        {
            "description": "Clouds",
            "temperature": 12.65,
            "feelsLike": 11.22,
            "windSpeed": 1.24
        },
        {
            "description": "Clouds",
            "temperature": 16.82,
            "feelsLike": 15.52,
            "windSpeed": 2.72
        },
        {
            "description": "Clouds",
            "temperature": 18.89,
            "feelsLike": 17.62,
            "windSpeed": 1.7
        },
        {
            "description": "Clouds",
            "temperature": 18.7,
            "feelsLike": 17.46,
            "windSpeed": 3.28
        },
        {
            "description": "Clouds",
            "temperature": 13.33,
            "feelsLike": 11.89,
            "windSpeed": 0.37
        },
        {
            "description": "Clouds",
            "temperature": 12.15,
            "feelsLike": 10.65,
            "windSpeed": 1.23
        },
        {
            "description": "Clouds",
            "temperature": 11.15,
            "feelsLike": 9.63,
            "windSpeed": 1.2
        },
        {
            "description": "Clouds",
            "temperature": 10.24,
            "feelsLike": 8.63,
            "windSpeed": 1.65
        },
        {
            "description": "Clouds",
            "temperature": 15.79,
            "feelsLike": 14.42,
            "windSpeed": 2.42
        },
        {
            "description": "Clouds",
            "temperature": 20.26,
            "feelsLike": 19.12,
            "windSpeed": 6.08
        },
        {
            "description": "Clouds",
            "temperature": 21.57,
            "feelsLike": 20.51,
            "windSpeed": 8.95
        },
        {
            "description": "Rain",
            "temperature": 16.67,
            "feelsLike": 15.72,
            "windSpeed": 2.31
        },
        {
            "description": "Clouds",
            "temperature": 15.49,
            "feelsLike": 14.48,
            "windSpeed": 1.38
        },
        {
            "description": "Clouds",
            "temperature": 15.85,
            "feelsLike": 14.64,
            "windSpeed": 4.46
        },
        {
            "description": "Clouds",
            "temperature": 13.54,
            "feelsLike": 12.15,
            "windSpeed": 2.81
        },
        {
            "description": "Clouds",
            "temperature": 13.3,
            "feelsLike": 11.89,
            "windSpeed": 4.47
        },
        {
            "description": "Clouds",
            "temperature": 19.11,
            "feelsLike": 18.12,
            "windSpeed": 7.61
        },
        {
            "description": "Clouds",
            "temperature": 23.16,
            "feelsLike": 22.24,
            "windSpeed": 10.01
        },
        {
            "description": "Clouds",
            "temperature": 23.57,
            "feelsLike": 22.61,
            "windSpeed": 11.19
        },
        {
            "description": "Clear",
            "temperature": 21.36,
            "feelsLike": 20.36,
            "windSpeed": 6.72
        },
        {
            "description": "Rain",
            "temperature": 13.47,
            "feelsLike": 12.6,
            "windSpeed": 2.16
        },
        {
            "description": "Rain",
            "temperature": 11.09,
            "feelsLike": 10.58,
            "windSpeed": 2.44
        },
        {
            "description": "Rain",
            "temperature": 9.49,
            "feelsLike": 9.49,
            "windSpeed": 1.01
        },
        {
            "description": "Rain",
            "temperature": 10.01,
            "feelsLike": 9.55,
            "windSpeed": 1.28
        },
        {
            "description": "Rain",
            "temperature": 11.09,
            "feelsLike": 10.34,
            "windSpeed": 1.54
        },
        {
            "description": "Rain",
            "temperature": 14.59,
            "feelsLike": 13.57,
            "windSpeed": 6.14
        },
        {
            "description": "Rain",
            "temperature": 15.66,
            "feelsLike": 14.48,
            "windSpeed": 7.58
        }
    ]
}
``` 
