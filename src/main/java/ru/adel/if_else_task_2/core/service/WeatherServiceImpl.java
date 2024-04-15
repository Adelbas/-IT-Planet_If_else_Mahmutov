package ru.adel.if_else_task_2.core.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Forecast;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.service.db.ForecastDbService;
import ru.adel.if_else_task_2.core.service.db.RegionDbService;
import ru.adel.if_else_task_2.core.service.db.WeatherDbService;
import ru.adel.if_else_task_2.public_interface.weather.WeatherMapper;
import ru.adel.if_else_task_2.public_interface.weather.WeatherService;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.PostWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherDbService weatherDbService;

    private final WeatherMapper weatherMapper;

    private final RegionDbService regionDbService;

    private final ForecastDbService forecastDbService;

    @Override
    @Transactional
    public PostWeatherResponseDto addWeather(@Valid WeatherRequestDto weatherRequestDto) {
        Region region = regionDbService.getRegionById(weatherRequestDto.regionId());

        if (weatherRequestDto.weatherForecast() != null) {
            List<Forecast> forecasts = forecastDbService.getForecasts(weatherRequestDto.weatherForecast());

            for (Forecast forecast : forecasts) {
                forecast.setRegion(region);
                forecastDbService.saveForecast(forecast);
            }
        }

        Weather weather = weatherDbService.saveWeather(
                Weather.builder()
                        .region(region)
                        .weatherCondition(weatherRequestDto.weatherCondition())
                        .humidity(weatherRequestDto.humidity())
                        .measurementDateTime(weatherRequestDto.measurementDateTime())
                        .precipitationAmount(weatherRequestDto.precipitationAmount())
                        .temperature(weatherRequestDto.temperature())
                        .windSpeed(weatherRequestDto.windSpeed())
                        .build()
        );

        return PostWeatherResponseDto.builder()
                .id(weather.getId())
                .weatherCondition(weather.getWeatherCondition())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .windSpeed(weather.getWindSpeed())
                .precipitationAmount(weather.getPrecipitationAmount())
                .measurementDateTime(weather.getMeasurementDateTime())
                .weatherForecast(weatherRequestDto.weatherForecast())
                .build();
    }

    @Override
    public GetWeatherResponseDto getWeather(@Min(1) Long regionId) {
        Region region = regionDbService.getRegionById(regionId);

        Weather weather = weatherDbService.getActualWeatherByRegion(region);

        List<Forecast> forecasts = forecastDbService.getActualForecastsByRegion(region);
        Long[] forecastsIds = forecasts.stream().map(Forecast::getId).toArray(Long[]::new);

        return GetWeatherResponseDto.builder()
                .id(regionId)
                .regionName(region.getName())
                .temperature(weather.getTemperature())
                .measurementDateTime(weather.getMeasurementDateTime())
                .humidity(weather.getHumidity())
                .precipitationAmount(weather.getPrecipitationAmount())
                .windSpeed(weather.getWindSpeed())
                .weatherCondition(weather.getWeatherCondition())
                .weatherForecast(forecastsIds)
                .build();
    }

    @Override
    public GetWeatherResponseDto updateWeather(@Valid WeatherRequestDto weatherRequestDto) {
        Region region = regionDbService.getRegionById(weatherRequestDto.regionId());
        Weather weather = weatherDbService.getActualWeatherByRegion(region);

        if (weatherRequestDto.weatherForecast() != null) {
            List<Forecast> forecasts = forecastDbService.getForecasts(weatherRequestDto.weatherForecast());

            for (Forecast forecast : forecasts) {
                forecast.setRegion(region);
                forecastDbService.saveForecast(forecast);
            }
        }

        weather.setTemperature(weatherRequestDto.temperature());
        weather.setHumidity(weatherRequestDto.humidity());
        weather.setWeatherCondition(weatherRequestDto.weatherCondition());
        weather.setWindSpeed(weatherRequestDto.windSpeed());
        weather.setMeasurementDateTime(weatherRequestDto.measurementDateTime());
        weather.setPrecipitationAmount(weatherRequestDto.precipitationAmount());

        weatherDbService.saveWeather(weather);

        return GetWeatherResponseDto.builder()
                .id(region.getId())
                .regionName(region.getName())
                .weatherCondition(weather.getWeatherCondition())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .windSpeed(weather.getWindSpeed())
                .precipitationAmount(weather.getPrecipitationAmount())
                .measurementDateTime(weather.getMeasurementDateTime())
                .weatherForecast(weatherRequestDto.weatherForecast())
                .build();
    }

    @Override
    public void deleteWeather(@Min(1) Long regionId) {
        Region region = regionDbService.getRegionById(regionId);
        Weather weather = weatherDbService.getActualWeatherByRegion(region);

        weatherDbService.deleteWeather(weather);
    }
}
