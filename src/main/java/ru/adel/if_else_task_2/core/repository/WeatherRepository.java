package ru.adel.if_else_task_2.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Page<Weather> findWeatherByMeasurementDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<Weather> findWeatherByMeasurementDateTimeBetweenAndWeatherCondition(
            LocalDateTime startDateTime, LocalDateTime endDateTime, WeatherCondition weatherCondition, PageRequest pageRequest
    );

    Page<Weather> findWeatherByMeasurementDateTimeBetweenAndRegion(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, PageRequest pageRequest
    );

    Page<Weather> findWeatherByMeasurementDateTimeBetweenAndRegionAndWeatherCondition(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Region region, WeatherCondition weatherCondition, PageRequest pageRequest
    );

    @Query(nativeQuery = true, value =
            "WITH RECURSIVE region_hierarchy AS (" +
                    "   SELECT id, name, parent_region_id " +
                    "   FROM region " +
                    "   WHERE (:regionId IS NULL OR id = :regionId) " +
                    "   UNION ALL " +
                    "   SELECT r.id, r.name, r.parent_region_id " +
                    "   FROM region r " +
                    "   INNER JOIN region_hierarchy rh ON r.parent_region_id = rh.id " +
                    ")" +
                    "SELECT w.id, w.region_id, " +
                    "    w.temperature, w.weather_condition, w.measurement_date_time, w.wind_speed, w.precipitation_amount, w.humidity " +
                    "FROM weather w " +
                    "INNER JOIN region_hierarchy r ON w.region_id = r.id " +
                    "WHERE (:startDateTime IS NULL OR w.measurement_date_time >= to_timestamp(:startDateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')) " +
                    "  AND (:endDateTime IS NULL OR w.measurement_date_time <= to_timestamp(:endDateTime, 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')) " +
                    "  AND (:weatherCondition IS NULL OR w.weather_condition = :weatherCondition) " +
                    "ORDER BY w.measurement_date_time " +
                    "OFFSET :form " +
                    "LIMIT :size"
    )
    List<Weather> searchWeatherInRegion(
            Long regionId,
            String startDateTime,
            String endDateTime,
            String weatherCondition,
            int form,
            int size
    );
}
