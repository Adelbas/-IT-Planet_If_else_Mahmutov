package ru.adel.if_else_task_2.public_interface.weather;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.PostWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherSearchRequestDto;
import ru.adel.if_else_task_2.model.RegionWeatherRequest;
import ru.adel.if_else_task_2.model.PostRegionWeatherResponse;
import ru.adel.if_else_task_2.model.RegionWeatherResponse;
import ru.adel.if_else_task_2.model.UpdateRegionWeatherRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface WeatherMapper {

    WeatherRequestDto toWeatherRequestDto (RegionWeatherRequest regionWeatherRequest);

    WeatherRequestDto toWeatherRequestDto (Long regionId, UpdateRegionWeatherRequest regionWeatherRequest);

    PostRegionWeatherResponse toPostRegionWeatherResponse (PostWeatherResponseDto postWeatherResponseDto);

    RegionWeatherResponse toRegionWeatherResponse (GetWeatherResponseDto getWeatherResponseDto);

    @Mapping(target = "regionName", source = "region.name")
    GetWeatherResponseDto toGetWeatherResponseDto(Weather weather);

    List<GetWeatherResponseDto> toWeatherResponseDtos(List<Weather> weathers);

    List<RegionWeatherResponse> toRegionWeatherResponses(List<GetWeatherResponseDto> weatherResponseDtos);

    WeatherSearchRequestDto toWeatherRequestDto(
            Optional<OffsetDateTime> startDateTime,
            Optional<OffsetDateTime> endDateTime,
            Optional<Long> regionId,
            Optional<ru.adel.if_else_task_2.model.WeatherCondition> weatherCondition,
            int form, int size
    );

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    default LocalDateTime optionalToLocalDateTime (Optional<OffsetDateTime> optional) {
        return optional.map(OffsetDateTime::toLocalDateTime).orElse(null);
    }

    default Long optionalToLong(Optional<Long> optional) {
        return optional.orElse(null);
    }

    default Optional<WeatherCondition> mapWeatherCondition(Optional<ru.adel.if_else_task_2.model.WeatherCondition> optional) {
        return optional.map(weatherCondition -> WeatherCondition.valueOf(weatherCondition.toString())).or(Optional::empty);
    }
}
