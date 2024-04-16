package ru.adel.if_else_task_2.public_interface.weather;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.adel.if_else_task_2.public_interface.weather.dto.GetWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.PostWeatherResponseDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherRequestDto;
import ru.adel.if_else_task_2.public_interface.weather.dto.WeatherSearchRequestDto;

import java.util.List;

public interface WeatherService {

    PostWeatherResponseDto addWeather(@Valid WeatherRequestDto weatherRequestDto);

    GetWeatherResponseDto getWeather(@Min(1) Long regionId);

    GetWeatherResponseDto updateWeather(@Valid WeatherRequestDto weatherRequestDto);

    void deleteWeather(@Min(1) Long regionId);

    List<GetWeatherResponseDto> searchWeather(WeatherSearchRequestDto weatherSearchRequestDto);
}