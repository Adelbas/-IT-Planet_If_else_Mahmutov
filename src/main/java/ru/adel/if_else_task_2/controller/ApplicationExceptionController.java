package ru.adel.if_else_task_2.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.adel.if_else_task_2.public_interface.exception.AlreadyAuthenticatedException;
import ru.adel.if_else_task_2.public_interface.exception.ClientAlreadyExistsException;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;
import ru.adel.if_else_task_2.public_interface.exception.RegionAlreadyExistsException;
import ru.adel.if_else_task_2.public_interface.exception.RegionIsParentException;
import ru.adel.if_else_task_2.public_interface.exception.RegionTypeAlreadyExistsException;
import ru.adel.if_else_task_2.public_interface.exception.RegionWithTypeExistsException;
import ru.adel.if_else_task_2.public_interface.exception.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Validation failed!")
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .details(new HashMap<>())
                .build();
        e.getBindingResult().getFieldErrors().forEach(error->errorResponse.details().put(error.getField(),error.getDefaultMessage()));
        return errorResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Validation failed!")
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .details(new HashMap<>())
                .build();
        e.getConstraintViolations().forEach(error -> errorResponse.details().put(error.getPropertyPath().toString(), error.getMessage()));
        return errorResponse;
    }

    @ExceptionHandler({ClientAlreadyExistsException.class, RegionTypeAlreadyExistsException.class, RegionAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(RuntimeException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
        return errorResponse;
    }

    @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
        return errorResponse;
    }

    @ExceptionHandler({AlreadyAuthenticatedException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(RuntimeException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
        return errorResponse;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
        return errorResponse;
    }

    @ExceptionHandler({RegionWithTypeExistsException.class, RegionIsParentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(RuntimeException e, @NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
        return errorResponse;
    }
}
