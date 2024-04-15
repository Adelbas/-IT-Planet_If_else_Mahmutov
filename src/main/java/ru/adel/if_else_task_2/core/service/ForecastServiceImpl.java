package ru.adel.if_else_task_2.core.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Forecast;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.service.db.ForecastDbService;
import ru.adel.if_else_task_2.core.service.db.RegionDbService;
import ru.adel.if_else_task_2.public_interface.forecast.ForecastService;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastRequestDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastResponseDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.UpdateForecastRequestDto;

@Service
@Validated
@RequiredArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private final ForecastDbService forecastDbService;

    private final RegionDbService regionDbService;

    @Override
    public ForecastResponseDto createForecast(@Valid ForecastRequestDto forecastRequestDto) {
        Region region = regionDbService.getRegionById(forecastRequestDto.regionId());

        Forecast forecast = forecastDbService.saveForecast(
                Forecast.builder()
                        .temperature(forecastRequestDto.temperature())
                        .weatherCondition(forecastRequestDto.weatherCondition())
                        .dateTime(forecastRequestDto.dateTime())
                        .region(region)
                        .build()
        );

        return ForecastResponseDto.builder()
                .id(forecast.getId())
                .regionId(region.getId())
                .weatherCondition(forecast.getWeatherCondition())
                .dateTime(forecast.getDateTime())
                .temperature(forecast.getTemperature())
                .build();
    }

    @Override
    public ForecastResponseDto updateForecast(@Valid UpdateForecastRequestDto forecastRequestDto) {
        Forecast forecast = forecastDbService.getForecast(forecastRequestDto.forecastId());

        forecast.setTemperature(forecastRequestDto.temperature());
        forecast.setWeatherCondition(forecastRequestDto.weatherCondition());
        forecast.setDateTime(forecastRequestDto.dateTime());

        forecast = forecastDbService.saveForecast(forecast);
        return ForecastResponseDto.builder()
                .id(forecast.getId())
                .regionId(forecast.getRegion().getId())
                .temperature(forecast.getTemperature())
                .dateTime(forecast.getDateTime())
                .weatherCondition(forecast.getWeatherCondition())
                .build();
    }

    @Override
    public ForecastResponseDto getForecast(@Min(1) Long id) {
        Forecast forecast = forecastDbService.getForecast(id);
        return ForecastResponseDto.builder()
                .id(forecast.getId())
                .regionId(forecast.getRegion().getId())
                .temperature(forecast.getTemperature())
                .dateTime(forecast.getDateTime())
                .weatherCondition(forecast.getWeatherCondition())
                .build();
    }

    @Override
    public void deleteForecast(@Min(1) Long id) {
        Forecast forecast = forecastDbService.getForecast(id);
        forecastDbService.deleteForecast(forecast);
    }
}
