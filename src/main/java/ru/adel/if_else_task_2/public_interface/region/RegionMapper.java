package ru.adel.if_else_task_2.public_interface.region;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.entity.Region;
import ru.adel.if_else_task_2.core.entity.RegionType;
import ru.adel.if_else_task_2.public_interface.region.dto.GetRegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionRequestDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.UpdateRegionRequestDto;
import ru.adel.if_else_task_2.model.RegionRequest;
import ru.adel.if_else_task_2.model.RegionResponse;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RegionMapper {

    RegionRequestDto toRegionRequestDto(RegionRequest regionRequest);

    UpdateRegionRequestDto toUpdateRegionRequestDto(Long id, RegionRequest regionRequest);

    RegionResponse toRegionResponse(RegionResponseDto regionResponseDto);

    RegionResponseDto toRegionResponseDto(Region region);

    @Mapping(source = "client", target = "accountId")
    GetRegionResponseDto toGetRegionResponseDto(Region region);

    RegionResponse toRegionResponse(GetRegionResponseDto getRegionResponseDto);

    default Long regionTypeToId(RegionType regionType) {
        return regionType.getId();
    }
    default String parentRegionToName(Region parentRegion) {
        return parentRegion.getName();
    }

    default Long clientToAccountId(Client client) {
        return client.getId();
    }
}
