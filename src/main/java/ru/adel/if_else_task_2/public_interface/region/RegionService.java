package ru.adel.if_else_task_2.public_interface.region;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.adel.if_else_task_2.public_interface.region.dto.GetRegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionRequestDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.UpdateRegionRequestDto;

public interface RegionService {

    GetRegionResponseDto getRegion(@Min(1) Long id);

    RegionResponseDto createRegion(@Valid RegionRequestDto regionRequestDto);

    RegionResponseDto updateRegion(@Valid UpdateRegionRequestDto updateRegionRequestDto);

    void deleteRegion(@Min(1) Long id);
}
