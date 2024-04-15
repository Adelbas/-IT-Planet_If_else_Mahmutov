package ru.adel.if_else_task_2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.api.AuthenticationApi;
import ru.adel.if_else_task_2.public_interface.authentication.AuthenticationMapper;
import ru.adel.if_else_task_2.public_interface.authentication.AuthenticationService;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginResponseDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationResponseDto;
import ru.adel.model.LoginRequest;
import ru.adel.model.LoginResponse;
import ru.adel.model.RegistrationRequest;
import ru.adel.model.RegistrationResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    private final AuthenticationMapper authenticationMapper;

    @Override
    public ResponseEntity<RegistrationResponse> registrate(RegistrationRequest registrationRequest) {
        log.info("Handle registration request {}", registrationRequest);
        RegistrationRequestDto registrationRequestDto = authenticationMapper.toRegistrationRequestDto(registrationRequest);

        RegistrationResponseDto registrationResponseDto = authenticationService.registrate(registrationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationMapper.toRegistrationResponse(registrationResponseDto));
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Handle login request {}", loginRequest);
        LoginRequestDto loginRequestDto = authenticationMapper.toLoginRequestDto(loginRequest);

        LoginResponseDto loginResponseDto = authenticationService.login(loginRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationMapper.toLoginResponse(loginResponseDto));
    }
}
