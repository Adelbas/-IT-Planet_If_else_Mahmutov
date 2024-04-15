package ru.adel.if_else_task_2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.if_else_task_2.public_interface.forecast.ForecastMapper;
import ru.adel.if_else_task_2.public_interface.forecast.ForecastService;
import ru.adel.api.WeatherForecastApi;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastRequestDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastResponseDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.UpdateForecastRequestDto;
import ru.adel.model.UpdateWeatherForecastRequest;
import ru.adel.model.WeatherForecastRequest;
import ru.adel.model.WeatherForecastResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ForecastController implements WeatherForecastApi {

    private final ForecastService forecastService;

    private final ForecastMapper forecastMapper;

    @Override
    public ResponseEntity<WeatherForecastResponse> getWeatherForecast(Long forecastId) {
        log.info("Handle get forecast request by id: {}", forecastId);

        ForecastResponseDto forecastResponseDto = forecastService.getForecast(forecastId);

        WeatherForecastResponse weatherForecastResponse = forecastMapper.toWeatherForecastResponse(forecastResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(weatherForecastResponse);
    }

    @Override
    public ResponseEntity<WeatherForecastResponse> createForecast(WeatherForecastRequest weatherForecastRequest) {
        log.info("Handle create forecast request: {}", weatherForecastRequest);

        ForecastRequestDto forecastRequestDto = forecastMapper.toForecastRequestDto(weatherForecastRequest);

        ForecastResponseDto forecastResponseDto = forecastService.createForecast(forecastRequestDto);

        WeatherForecastResponse weatherForecastResponse = forecastMapper.toWeatherForecastResponse(forecastResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(weatherForecastResponse);
    }

    @Override
    public ResponseEntity<WeatherForecastResponse> updateWeatherForecast(Long forecastId, UpdateWeatherForecastRequest updateWeatherForecastRequest) {
        log.info("Handle update forecast with id {} request: {}", forecastId, updateWeatherForecastRequest);

        UpdateForecastRequestDto updateForecastRequestDto = forecastMapper.toUpdateForecastRequestDto(forecastId, updateWeatherForecastRequest);

        ForecastResponseDto forecastResponseDto = forecastService.updateForecast(updateForecastRequestDto);

        WeatherForecastResponse weatherForecastResponse = forecastMapper.toWeatherForecastResponse(forecastResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(weatherForecastResponse);
    }

    @Override
    public ResponseEntity<WeatherForecastResponse> deleteWeatherForecast(Long forecastId) {
        log.info("Handle delete forecast request by id: {}", forecastId);

        forecastService.deleteForecast(forecastId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
