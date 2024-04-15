package ru.adel.if_else_task_2.public_interface.weather.dto;

import lombok.Builder;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;

@Builder
public record GetWeatherResponseDto(
        Long id,
        String regionName,
        Float temperature,
        Float humidity,
        Float windSpeed,
        Float precipitationAmount,
        LocalDateTime measurementDateTime,
        WeatherCondition weatherCondition,
        Long[] weatherForecast
) {
}
