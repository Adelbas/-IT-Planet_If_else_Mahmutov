package ru.adel.if_else_task_2.public_interface.account.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record AccountSearchRequestDto(
        String firstName,
        String lastName,
        String email,
        @Min(0)
        int form,
        @Min(1)
        int size
) { }
