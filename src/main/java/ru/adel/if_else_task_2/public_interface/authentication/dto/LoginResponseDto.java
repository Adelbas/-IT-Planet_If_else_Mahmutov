package ru.adel.if_else_task_2.public_interface.authentication.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        Long id
) { }
