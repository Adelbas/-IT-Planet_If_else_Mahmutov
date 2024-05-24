package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.core.repository.RegionRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionDbService {

    private final RegionRepository regionRepository;

    public Region getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Region not found with id: " + id));
    }

    public Region getRegionByName(String name) {
        return regionRepository.findRegionByName(name)
                .orElseThrow(() -> new NotFoundException("Region not found with name: " + name));
    }

    public Optional<Region> findRegionByLatitudeAndLongitude(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        return regionRepository.findRegionByLatitude1AndLongitude1AndLatitude2AndLongitude2(
                latitude1,
                longitude1,
                latitude2,
                longitude2
        );
    }

    public boolean checkRegionsByRegionType(RegionType regionType) {
        return regionRepository.existsRegionByRegionType(regionType);
    }

    public boolean checkIfRegionIsParent(Region regionToCheck) {
        return regionRepository.existsRegionByParentRegion(regionToCheck);
    }

    public Region saveRegion(Region region) {
        log.info("Saving region to db: {}", region);
        return regionRepository.save(region);
    }

    public void deleteRegion(Region region) {
        log.info("Deleting region from db: {}", region);
        regionRepository.delete(region);
    }
}
