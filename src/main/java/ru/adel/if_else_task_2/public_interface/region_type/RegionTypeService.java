package ru.adel.if_else_task_2.public_interface.region_type;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeRequestDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeResponseDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.UpdateRegionTypeRequestDto;

public interface RegionTypeService {

    RegionTypeResponseDto getRegionType(@Min(1) Long id);

    RegionTypeResponseDto createRegionType(@Valid RegionTypeRequestDto regionTypeRequestDto);

    RegionTypeResponseDto updateRegionType(@Valid UpdateRegionTypeRequestDto updateRegionTypeRequestDto);

    void deleteRegionType(@Min(1) Long id);
}
