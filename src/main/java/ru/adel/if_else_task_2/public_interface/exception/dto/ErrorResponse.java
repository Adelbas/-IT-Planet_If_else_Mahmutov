package ru.adel.if_else_task_2.public_interface.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse (
        int status,
        String message,
        LocalDateTime timestamp,
        String path,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String,String> details
) { }
