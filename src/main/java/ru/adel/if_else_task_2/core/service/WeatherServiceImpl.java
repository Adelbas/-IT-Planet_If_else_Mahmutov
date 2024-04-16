package ru.adel.if_else_task_2.core.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Forecast;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;
import ru.adel.if_else_task_2.core.service.db.ForecastDbService;
import ru.adel.if_else_task_2.core.service.db.RegionDbService;
import ru.adel.if_else_task_2.core.service.db.WeatherDbService;
import ru.adel.if_else_task_2.public_interface.weather.WeatherMapper;
import ru.adel.if_else_task_2.public_interface.weather.WeatherService;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.PostWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherSearchRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final LocalDateTime MAXIMUM_TIMESTAMP = LocalDateTime.now().plusYears(100);
    private static final LocalDateTime MINIMUM_TIMESTAMP = LocalDateTime.now().minusYears(100);

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

    @Override
    public List<GetWeatherResponseDto> searchWeather(WeatherSearchRequestDto weatherSearchRequestDto) {
        PageRequest pageRequest = PageRequest.of(weatherSearchRequestDto.form(), weatherSearchRequestDto.size());

        LocalDateTime startDateTime = MINIMUM_TIMESTAMP;
        LocalDateTime endDateTime = MAXIMUM_TIMESTAMP;

        if (weatherSearchRequestDto.startDateTime() != null) {
            startDateTime = weatherSearchRequestDto.startDateTime();
        }
        if (weatherSearchRequestDto.endDateTime() != null) {
            endDateTime = weatherSearchRequestDto.endDateTime();
        }

        if (weatherSearchRequestDto.regionId() == null) {
            return searchWithoutRegion(startDateTime, endDateTime, weatherSearchRequestDto.weatherCondition(), pageRequest);
        }

        Region region = regionDbService.getRegionById(weatherSearchRequestDto.regionId());

        if (weatherSearchRequestDto.weatherCondition().isEmpty()) {
            return searchWithRegionAndWithoutWeatherCondition(startDateTime, endDateTime, region, pageRequest);
        }

        WeatherCondition weatherCondition = WeatherCondition.valueOf(weatherSearchRequestDto.weatherCondition().get().toString());
        List<Weather> weathers = weatherDbService.searchWithAllParams(startDateTime, endDateTime, region, weatherCondition, pageRequest);

        return weatherMapper.toWeatherResponseDtos(weathers);
    }

    private List<GetWeatherResponseDto> searchWithoutRegion(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Optional<WeatherCondition> weatherCondition, PageRequest pageRequest) {
        if (weatherCondition.isEmpty()) {
            return searchWithoutRegionAndWeatherCondition(startDateTime, endDateTime, pageRequest);
        }

        WeatherCondition actualWeatherCondition = WeatherCondition.valueOf(weatherCondition.get().toString());

        List<Weather> weathers = weatherDbService.searchWeatherWithoutRegion(
                startDateTime, endDateTime, actualWeatherCondition, pageRequest
        );

        return weatherMapper.toWeatherResponseDtos(weathers);
    }

    private List<GetWeatherResponseDto> searchWithoutRegionAndWeatherCondition(LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest) {
        List<Weather> weathers = weatherDbService.searchWeatherWithoutRegionAndWeatherCondition(startDateTime, endDateTime, pageRequest);

        return weatherMapper.toWeatherResponseDtos(weathers);
    }

    private List<GetWeatherResponseDto> searchWithRegionAndWithoutWeatherCondition(LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, PageRequest pageRequest) {

        List<Weather> weathers = weatherDbService.searchWithRegionAndWithoutWeatherCondition(startDateTime, endDateTime, region, pageRequest);

        return weatherMapper.toWeatherResponseDtos(weathers);
    }
}
