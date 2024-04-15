package ru.adel.if_else_task_2.public_interface.account.dto;

import lombok.Builder;

@Builder
public record AccountResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) { }
