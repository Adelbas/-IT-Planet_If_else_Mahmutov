package ru.adel.if_else_task_2.public_interface.forecast.dto;

import lombok.Builder;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;

@Builder
public record ForecastResponseDto(
        Long id,
        Long regionId,
        LocalDateTime dateTime,
        Float temperature,
        WeatherCondition weatherCondition
) { }
