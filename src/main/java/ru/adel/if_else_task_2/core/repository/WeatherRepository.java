package ru.adel.if_else_task_2.core.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.Weather;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findFirstByRegionOrderByMeasurementDateTimeDesc(Region region);

    Optional<List<Weather>> findWeatherByMeasurementDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Optional<List<Weather>> findWeatherByMeasurementDateTimeBetweenAndWeatherCondition(
            LocalDateTime startDateTime, LocalDateTime endDateTime, WeatherCondition weatherCondition, PageRequest pageRequest
    );

    Optional<List<Weather>> findWeatherByMeasurementDateTimeBetweenAndRegion(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, PageRequest pageRequest
    );

    Optional<List<Weather>> findWeatherByMeasurementDateTimeBetweenAndRegionAndWeatherCondition(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, WeatherCondition weatherCondition, PageRequest pageRequest
    );
}
