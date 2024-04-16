package ru.adel.if_else_task_2.public_interface.weather.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
public record WeatherSearchRequestDto(
         @Min(1)
         Long regionId,
         LocalDateTime startDateTime,
         LocalDateTime endDateTime,
         Optional<WeatherCondition> weatherCondition,
         @Min(0)
         int form,
         @Min(1)
         int size
) { }
