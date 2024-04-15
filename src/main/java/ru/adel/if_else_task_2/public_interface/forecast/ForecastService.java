package ru.adel.if_else_task_2.public_interface.forecast;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastRequestDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.ForecastResponseDto;
import ru.adel.if_else_task_2.public_interface.forecast.dto.UpdateForecastRequestDto;

public interface ForecastService {

    ForecastResponseDto createForecast(@Valid ForecastRequestDto forecastRequestDto);

    ForecastResponseDto updateForecast(@Valid UpdateForecastRequestDto forecastRequestDto);

    ForecastResponseDto getForecast(@Min(1) Long id);

    void deleteForecast(@Min(1) Long id);
}
