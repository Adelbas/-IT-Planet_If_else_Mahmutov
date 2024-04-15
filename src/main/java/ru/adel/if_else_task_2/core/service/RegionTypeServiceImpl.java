package ru.adel.if_else_task_2.core.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.core.service.db.RegionDbService;
import ru.adel.if_else_task_2.core.service.db.RegionTypeDbService;
import ru.adel.if_else_task_2.public_interface.exception.RegionTypeAlreadyExistsException;
import ru.adel.if_else_task_2.public_interface.exception.RegionWithTypeExistsException;
import ru.adel.if_else_task_2.public_interface.region_type.RegionTypeMapper;
import ru.adel.if_else_task_2.public_interface.region_type.RegionTypeService;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeRequestDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeResponseDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.UpdateRegionTypeRequestDto;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class RegionTypeServiceImpl implements RegionTypeService {

    private final RegionTypeDbService regionTypeDbService;

    private final RegionDbService regionDbService;

    private final RegionTypeMapper regionTypeMapper;

    @Override
    public RegionTypeResponseDto getRegionType(@Min(1) Long id) {
        return regionTypeMapper.toRegionTypeResponse(
                regionTypeDbService.getRegionTypeById(id)
        );
    }

    @Override
    public RegionTypeResponseDto createRegionType(@Valid RegionTypeRequestDto regionTypeRequestDto) {
        Optional<RegionType> sameRegionType = regionTypeDbService.findRegionTypeByType(regionTypeRequestDto.type());

        if (sameRegionType.isPresent()) {
            throw new RegionTypeAlreadyExistsException(regionTypeRequestDto.type());
        }

        RegionType regionType = regionTypeDbService.saveRegion(
                RegionType.builder()
                        .type(regionTypeRequestDto.type())
                        .build()
        );

        return regionTypeMapper.toRegionTypeResponse(regionType);
    }

    @Override
    public RegionTypeResponseDto updateRegionType(@Valid UpdateRegionTypeRequestDto updateRegionTypeRequestDto) {
        RegionType regionType = regionTypeDbService.getRegionTypeById(updateRegionTypeRequestDto.id());

        Optional<RegionType> sameRegionType = regionTypeDbService.findRegionTypeByType(updateRegionTypeRequestDto.type());

        if (sameRegionType.isPresent() && !regionType.getId().equals(sameRegionType.get().getId())) {
            throw new RegionTypeAlreadyExistsException(updateRegionTypeRequestDto.type());
        }

        regionType.setType(updateRegionTypeRequestDto.type());
        return regionTypeMapper.toRegionTypeResponse(
                regionTypeDbService.saveRegion(regionType)
        );
    }

    @Override
    public void deleteRegionType(@Min(1) Long id) {
        RegionType regionType = regionTypeDbService.getRegionTypeById(id);

        if (regionDbService.checkRegionsByRegionType(regionType)) {
            throw new RegionWithTypeExistsException(regionType.getType());
        }

        regionTypeDbService.deleteRegionType(regionType);
    }
}
