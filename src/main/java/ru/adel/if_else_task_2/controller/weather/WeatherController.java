package ru.adel.if_else_task_2.controller.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.if_else_task_2.api.WeatherApi;
import ru.adel.if_else_task_2.public_interface.region.RegionMapper;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.WeatherMapper;
import ru.adel.if_else_task_2.public_interface.weather.WeatherService;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;
import ru.adel.if_else_task_2.model.PostRegionWeatherResponse;
import ru.adel.if_else_task_2.model.RegionResponse;
import ru.adel.if_else_task_2.model.RegionWeatherRequest;
import ru.adel.if_else_task_2.model.RegionWeatherResponse;
import ru.adel.if_else_task_2.model.UpdateRegionWeatherRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherController implements WeatherApi {

    private final WeatherService weatherService;

    private final WeatherMapper weatherMapper;

    private final RegionMapper regionMapper;

    @Override
    public ResponseEntity<PostRegionWeatherResponse> addWeather(RegionWeatherRequest regionWeatherRequest) {
        log.info("Handle add weather request: {}", regionWeatherRequest);

        WeatherRequestDto weatherRequestDto = weatherMapper.toWeatherRequestDto(regionWeatherRequest);

        PostRegionWeatherResponse postRegionWeatherResponse = weatherMapper.toPostRegionWeatherResponse(
                weatherService.addWeather(weatherRequestDto)
        );

        return ResponseEntity.status(HttpStatus.OK).body(postRegionWeatherResponse);
    }

    @Override
    public ResponseEntity<RegionWeatherResponse> getRegionWeather(Long regionId) {
        log.info("Handle get weather request by region with id: {}", regionId);

        RegionWeatherResponse regionWeatherResponse = weatherMapper.toRegionWeatherResponse(
                weatherService.getWeather(regionId)
        );

        return ResponseEntity.status(HttpStatus.OK).body(regionWeatherResponse);
    }

    @Override
    public ResponseEntity<RegionWeatherResponse> updateRegionWeather(Long regionId, UpdateRegionWeatherRequest updateRegionWeatherRequest) {
        log.info("Handle update weather request for region with id {}: {}", regionId, updateRegionWeatherRequest);

        WeatherRequestDto weatherRequestDto = weatherMapper.toWeatherRequestDto(regionId, updateRegionWeatherRequest);

        RegionWeatherResponse regionWeatherResponse = weatherMapper.toRegionWeatherResponse(
                weatherService.updateWeather(weatherRequestDto)
        );

        return ResponseEntity.status(HttpStatus.OK).body(regionWeatherResponse);
    }

    @Override
    public ResponseEntity<Void> deleteRegionWeather(Long regionId) {
        log.info("Handle delete weather request by region with id: {}", regionId);

        weatherService.deleteWeather(regionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<RegionWeatherResponse> addWeatherToRegion(Long regionId, Long weatherId) {
        log.info("Handle add weather {} to region {} request", weatherId, regionId);

        GetWeatherResponseDto getWeatherResponseDto = weatherService.addWeatherToRegion(weatherId, regionId);

        RegionWeatherResponse regionWeatherResponse = weatherMapper.toRegionWeatherResponse(getWeatherResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionWeatherResponse);
    }

    @Override
    public ResponseEntity<RegionResponse> deleteWeatherFromRegion(Long regionId, Long weatherId) {
        log.info("Handle delete weather {} from region {} request", weatherId, regionId);

        RegionResponseDto regionResponseDto = weatherService.deleteWeatherFromRegion(weatherId, regionId);

        RegionResponse regionResponse = regionMapper.toRegionResponse(regionResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionResponse);
    }
}
