package ru.adel.if_else_task_2.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.api.WeatherSearchApi;
import ru.adel.if_else_task_2.public_interface.weather.WeatherMapper;
import ru.adel.if_else_task_2.public_interface.weather.WeatherService;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherSearchRequestDto;
import ru.adel.model.RegionWeatherResponse;
import ru.adel.model.WeatherCondition;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherSearchController implements WeatherSearchApi {

    private static final int DEFAULT_SIZE = 1;

    private static final int DEFAULT_FORM = 0;

    private final WeatherService weatherService;

    private final WeatherMapper weatherMapper;

    @Override
    public ResponseEntity<List<RegionWeatherResponse>> searchWeather(
            Optional<OffsetDateTime> startDateTime,
            Optional<OffsetDateTime> endDateTime,
            Optional<Long> regionId,
            Optional<WeatherCondition> weatherCondition,
            Optional<Integer> form, Optional<Integer> size) {
        log.info("Handle weather search request");

        WeatherSearchRequestDto weatherSearchRequestDto = weatherMapper.toWeatherRequestDto(
                startDateTime,
                endDateTime,
                regionId,
                weatherCondition,
                form.orElse(DEFAULT_FORM),
                size.orElse(DEFAULT_SIZE)
        );
        log.info(weatherSearchRequestDto.toString());

        List<GetWeatherResponseDto> weatherResponseDtos = weatherService.searchWeather(weatherSearchRequestDto);

        List<RegionWeatherResponse> regionWeatherResponses = weatherMapper.toRegionWeatherResponses(weatherResponseDtos);

        return ResponseEntity.status(HttpStatus.OK).body(regionWeatherResponses);
    }
}
