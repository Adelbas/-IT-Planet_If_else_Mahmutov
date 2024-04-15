package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.Forecast;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.repository.ForecastRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForecastDbService {

    private final ForecastRepository forecastRepository;

    public Forecast getForecast(Long id) {
        return forecastRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Forecast not found"));
    }

    public List<Forecast> getForecasts (Long[] ids) {
        List<Forecast> forecasts = new ArrayList<>();
        for (int i = 0; i<ids.length; i++) {
            forecasts.add(getForecast(ids[i]));
        }

        return forecasts;
    }

    public List<Forecast> getActualForecastsByRegion (Region region) {
        return forecastRepository.findFirst10ForecastByRegionOrderByDateTimeDesc(region)
                .orElseThrow(()->new NotFoundException("Forecasts not found for region with id: " + region.getId()));
    }

    public Forecast saveForecast(Forecast forecast) {
        log.info("Saving forecast to db: {}", forecast);
        return forecastRepository.save(forecast);
    }

    public void deleteForecast(Forecast forecast) {
        log.info("Deleting forecast from db: {}", forecast);
        forecastRepository.delete(forecast);
    }
}
