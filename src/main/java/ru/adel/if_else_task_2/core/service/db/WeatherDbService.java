package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.repository.WeatherRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

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

    public void deleteWeather(Weather weather) {
        weatherRepository.delete(weather);
    }
}
