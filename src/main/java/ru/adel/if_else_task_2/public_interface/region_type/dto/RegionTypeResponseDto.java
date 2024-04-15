package ru.adel.if_else_task_2.public_interface.region_type.dto;

import lombok.Builder;

@Builder
public record RegionTypeResponseDto(
        Long id,
        String type
) { }
