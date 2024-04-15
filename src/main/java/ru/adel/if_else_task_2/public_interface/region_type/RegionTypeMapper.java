package ru.adel.if_else_task_2.public_interface.region_type;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeRequestDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeResponseDto;
import ru.adel.if_else_task_2.public_interface.region_type.dto.UpdateRegionTypeRequestDto;
import ru.adel.model.RegionTypeRequest;
import ru.adel.model.RegionTypeResponse;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RegionTypeMapper {

    RegionTypeResponseDto toRegionTypeResponse(RegionType regionType);

    RegionTypeRequestDto toRegionTypeRequestDto(RegionTypeRequest regionTypeRequest);

    RegionTypeResponse toRegionTypeResponse(RegionTypeResponseDto regionTypeResponseDto);

    UpdateRegionTypeRequestDto toUpdateRegionTypeRequestDto(Long id, RegionTypeRequest regionTypeRequest);
}
