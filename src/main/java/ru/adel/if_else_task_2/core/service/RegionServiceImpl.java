package ru.adel.if_else_task_2.core.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.core.service.db.ClientDbService;
import ru.adel.if_else_task_2.core.service.db.RegionDbService;
import ru.adel.if_else_task_2.core.service.db.RegionTypeDbService;
import ru.adel.if_else_task_2.public_interface.exception.RegionAlreadyExistsException;
import ru.adel.if_else_task_2.public_interface.exception.RegionIsParentException;
import ru.adel.if_else_task_2.public_interface.region.RegionMapper;
import ru.adel.if_else_task_2.public_interface.region.RegionService;
import ru.adel.if_else_task_2.public_interface.region.dto.GetRegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionRequestDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.UpdateRegionRequestDto;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionDbService regionDbService;

    private final RegionTypeDbService regionTypeDbService;

    private final ClientDbService clientDbService;

    private final RegionMapper regionMapper;

    @Override
    public GetRegionResponseDto getRegion(@Min(1) Long id) {
        return regionMapper.toGetRegionResponseDto(regionDbService.getRegionById(id));
    }

    @Override
    public RegionResponseDto createRegion(@Valid RegionRequestDto regionRequestDto) {
        checkRegionToDuplicateByCoordinates(regionRequestDto.latitude1(), regionRequestDto.longitude1(),
                regionRequestDto.latitude2(), regionRequestDto.longitude2());

        Long currentClientId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        Client client = clientDbService.getClientById(currentClientId);

        Region region = Region.builder()
                .name(regionRequestDto.name())
                .client(client)
                .latitude1(regionRequestDto.latitude1())
                .longitude1(regionRequestDto.longitude1())
                .latitude2(regionRequestDto.latitude2())
                .longitude2(regionRequestDto.longitude2())
                .build();

        if (regionRequestDto.regionType() != null) {
            RegionType regionType = regionTypeDbService.getRegionTypeById(regionRequestDto.regionType());
            region.setRegionType(regionType);
        }

        if (regionRequestDto.parentRegion() != null) {
            Region parentRegion = regionDbService.getRegionByName(regionRequestDto.parentRegion());
            region.setParentRegion(parentRegion);
        }

        return regionMapper.toRegionResponseDto(regionDbService.saveRegion(region));
    }

    @Override
    public RegionResponseDto updateRegion(@Valid UpdateRegionRequestDto updateRegionRequestDto) {
        Region regionToUpdate = regionDbService.getRegionById(updateRegionRequestDto.id());

        if (!regionToUpdate.getLatitude1().equals(updateRegionRequestDto.latitude1())
                && !regionToUpdate.getLongitude1().equals(updateRegionRequestDto.longitude1())
                && !regionToUpdate.getLatitude2().equals(updateRegionRequestDto.latitude2())
                && !regionToUpdate.getLongitude2().equals(updateRegionRequestDto.longitude2())) {
            checkRegionToDuplicateByCoordinates(updateRegionRequestDto.latitude1(), updateRegionRequestDto.longitude1(),
                    updateRegionRequestDto.latitude2(), updateRegionRequestDto.longitude2());
        }

        regionToUpdate.setName(updateRegionRequestDto.name());
        regionToUpdate.setLatitude1(updateRegionRequestDto.latitude1());
        regionToUpdate.setLongitude1(updateRegionRequestDto.longitude1());
        regionToUpdate.setLatitude2(updateRegionRequestDto.latitude1());
        regionToUpdate.setLongitude2(updateRegionRequestDto.longitude2());

        if (updateRegionRequestDto.regionType() != null) {
            RegionType regionType = regionTypeDbService.getRegionTypeById(updateRegionRequestDto.regionType());
            regionToUpdate.setRegionType(regionType);
        }

        if (updateRegionRequestDto.parentRegion() != null) {
            Region newParentRegion = regionDbService.getRegionByName(updateRegionRequestDto.parentRegion());
            regionToUpdate.setParentRegion(newParentRegion);
        }

        return regionMapper.toRegionResponseDto(regionDbService.saveRegion(regionToUpdate));
    }

    @Override
    public void deleteRegion(@Min(1) Long id) {
        Region region = regionDbService.getRegionById(id);

        if (regionDbService.checkIfRegionIsParent(region)) {
            throw new RegionIsParentException(id);
        }

        regionDbService.deleteRegion(region);
    }

    private void checkRegionToDuplicateByCoordinates(Double latitude1, Double longitude1, Double latitude2, Double longitude2) throws RegionAlreadyExistsException{
        Optional<Region> sameRegion = regionDbService.findRegionByLatitudeAndLongitude(
                latitude1,
                longitude1,
                latitude2,
                longitude2
        );

        if (sameRegion.isPresent()) {
            throw new RegionAlreadyExistsException(latitude1, longitude1, latitude2, longitude2);
        }
    }
}
