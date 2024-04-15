package ru.adel.if_else_task_2.public_interface.forecast;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastRequestDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastResponseDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.UpdateForecastRequestDto;
import ru.adel.model.WeatherForecastRequest;
import ru.adel.model.WeatherForecastResponse;
import ru.adel.model.UpdateWeatherForecastRequest;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ForecastMapper {

    ForecastRequestDto toForecastRequestDto(WeatherForecastRequest weatherForecastRequest);

    UpdateForecastRequestDto toUpdateForecastRequestDto(Long forecastId, UpdateWeatherForecastRequest updateWeatherForecastRequest);

    WeatherForecastResponse toWeatherForecastResponse(ForecastResponseDto forecastResponseDto);

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
