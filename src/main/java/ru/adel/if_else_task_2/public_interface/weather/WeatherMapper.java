package ru.adel.if_else_task_2.public_interface.weather;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.PostWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;
import ru.adel.model.RegionWeatherRequest;
import ru.adel.model.PostRegionWeatherResponse;
import ru.adel.model.RegionWeatherResponse;
import ru.adel.model.UpdateRegionWeatherRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface WeatherMapper {

    WeatherRequestDto toWeatherRequestDto (RegionWeatherRequest regionWeatherRequest);

    WeatherRequestDto toWeatherRequestDto (Long regionId, UpdateRegionWeatherRequest regionWeatherRequest);

    PostRegionWeatherResponse toPostRegionWeatherResponse (PostWeatherResponseDto postWeatherResponseDto);

    RegionWeatherResponse toRegionWeatherResponse (GetWeatherResponseDto getWeatherResponseDto);

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
