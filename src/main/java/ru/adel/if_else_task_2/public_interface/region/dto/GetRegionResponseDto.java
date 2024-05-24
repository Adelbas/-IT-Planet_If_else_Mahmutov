package ru.adel.if_else_task_2.public_interface.region.dto;

import lombok.Builder;

@Builder
public record GetRegionResponseDto(
        Long id,
        String name,
        Long accountId,
        String parentRegion,
        Long regionType,
        Double latitude1,
        Double longitude1,
        Double latitude2,
        Double longitude2
) { }
