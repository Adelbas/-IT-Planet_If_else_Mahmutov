package ru.adel.if_else_task_2.public_interface.authentication.dto;

import lombok.Builder;

@Builder
public record RegistrationResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
