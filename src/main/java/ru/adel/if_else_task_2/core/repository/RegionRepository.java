package ru.adel.if_else_task_2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.RegionType;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    boolean existsRegionByRegionType(RegionType regionType);

    Optional<Region> findRegionByLatitude1AndLongitude1AndLatitude2AndLongitude2(Double latitude1, Double longitude1, Double latitude2, Double longitude2);

    Optional<Region> findRegionByName(String name);

    boolean existsRegionByParentRegion(Region parentRegion);
}
