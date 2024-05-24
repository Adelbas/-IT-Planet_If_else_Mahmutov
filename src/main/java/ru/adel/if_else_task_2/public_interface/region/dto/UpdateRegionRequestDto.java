package ru.adel.if_else_task_2.public_interface.region.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateRegionRequestDto (
        @Min(1)
        Long id,
        @NotBlank
        String name,
        String parentRegion,
        Long regionType,
        @NotNull
        Double latitude1,
        @NotNull
        Double longitude1,
        @NotNull
        Double latitude2,
        @NotNull
        Double longitude2
) { }
