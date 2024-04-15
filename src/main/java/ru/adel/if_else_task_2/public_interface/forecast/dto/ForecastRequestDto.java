package ru.adel.if_else_task_2.public_interface.forecast.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;

@Builder
public record ForecastRequestDto(
        @Min(1)
        Long regionId,
        @NotNull
        LocalDateTime dateTime,
        @NotNull
        Float temperature,
        @NotNull
        WeatherCondition weatherCondition
) { }
