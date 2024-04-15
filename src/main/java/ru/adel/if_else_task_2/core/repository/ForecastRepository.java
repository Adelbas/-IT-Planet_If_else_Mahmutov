package ru.adel.if_else_task_2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.if_else_task_2.core.entity.Forecast;
import ru.adel.if_else_task_2.core.entity.Region;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    Optional<List<Forecast>> findFirst10ForecastByRegionOrderByDateTimeDesc(Region region);
}
