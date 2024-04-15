package ru.adel.if_else_task_2.public_interface.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegistrationRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        String email,
        @NotBlank
        String password
) { }
