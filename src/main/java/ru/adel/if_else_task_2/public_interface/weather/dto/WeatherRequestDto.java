package ru.adel.if_else_task_2.public_interface.weather.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;

@Builder
public record WeatherRequestDto(
        @Min(1)
        Long regionId,
        @NotNull
        Float temperature,
        @NotNull
        Float humidity,
        @Min(0)
        Float windSpeed,
        @Min(0)
        Float precipitationAmount,
        @NotNull
        LocalDateTime measurementDateTime,
        @NotNull
        WeatherCondition weatherCondition,
        Long[] weatherForecast
) { }