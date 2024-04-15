package ru.adel.if_else_task_2.public_interface.region_type.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegionTypeRequestDto(
        @NotBlank
        String type
) { }
