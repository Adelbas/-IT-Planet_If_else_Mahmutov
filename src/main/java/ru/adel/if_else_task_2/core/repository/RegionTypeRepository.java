package ru.adel.if_else_task_2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.if_else_task_2.core.entity.RegionType;

import java.util.Optional;

@Repository
public interface RegionTypeRepository extends JpaRepository<RegionType, Long> {

    Optional<RegionType> findRegionTypeByType(String type);
}
