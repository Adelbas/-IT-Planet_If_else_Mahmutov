package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;
import ru.adel.if_else_task_2.core.repository.WeatherRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherDbService {

    private final WeatherRepository weatherRepository;

    public Weather saveWeather (Weather weather) {
        log.info("Saving weather to db: {}", weather);
        return weatherRepository.save(weather);
    }

    public Weather getActualWeatherByRegion(Region region) {
        return weatherRepository.findFirstByRegionOrderByMeasurementDateTimeDesc(region)
                .orElseThrow(()->new NotFoundException("Weather not found"));
    }

    public Weather getWeatherById(Long weatherId) {
        return weatherRepository.findById(weatherId)
                .orElseThrow(()->new NotFoundException("Weather not found"));
    }

    public void deleteWeather(Weather weather) {
        weatherRepository.delete(weather);
    }

    public List<Weather> searchWeatherWithoutRegionAndWeatherCondition(LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest) {
        return weatherRepository.findWeatherByMeasurementDateTimeBetween(
                startDateTime,
                endDateTime,
                pageRequest
        ).orElse(new ArrayList<>());
    }

    public List<Weather> searchWeatherWithoutRegion(
            LocalDateTime startDateTime, LocalDateTime endDateTime, WeatherCondition weatherCondition, PageRequest pageRequest) {
        return weatherRepository.findWeatherByMeasurementDateTimeBetweenAndWeatherCondition(
                startDateTime,
                endDateTime,
                weatherCondition,
                pageRequest
        ).orElse(new ArrayList<>());
    }

    public List<Weather> searchWithRegionAndWithoutWeatherCondition(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, PageRequest pageRequest) {
        return weatherRepository.findWeatherByMeasurementDateTimeBetweenAndRegion(
                startDateTime,
                endDateTime,
                region,
                pageRequest
        ).orElse(new ArrayList<>());
    }

    public List<Weather> searchWithAllParams(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Region region,
            WeatherCondition weatherCondition,
            PageRequest pageRequest) {
        return weatherRepository.findWeatherByMeasurementDateTimeBetweenAndRegionAndWeatherCondition(
                startDateTime,
                endDateTime,
                region,
                weatherCondition,
                pageRequest
        ).orElse(new ArrayList<>());
    }
}
