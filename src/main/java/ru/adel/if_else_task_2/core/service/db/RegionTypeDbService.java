package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.core.repository.RegionTypeRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionTypeDbService {

    private final RegionTypeRepository regionTypeRepository;

    public RegionType getRegionTypeById(Long id) {
        return regionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Region type not found"));
    }

    public Optional<RegionType> findRegionTypeById(Long id) {
        return regionTypeRepository.findById(id);
    }

    public Optional<RegionType> findRegionTypeByType(String type) {
        return regionTypeRepository.findRegionTypeByType(type);
    }

    public RegionType saveRegion(RegionType regionType) {
        log.info("Saving region type to db: {}", regionType);
        return regionTypeRepository.save(regionType);
    }

    public void deleteRegionType(RegionType regionType) {
        log.info("Deleting region type from db: {}", regionType);
        regionTypeRepository.delete(regionType);
    }
}
