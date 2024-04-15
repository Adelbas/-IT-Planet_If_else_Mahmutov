package ru.adel.if_else_task_2.public_interface.region.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegionRequestDto(
        @NotBlank
        String name,
        String parentRegion,
        Long regionType,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude
) { }
