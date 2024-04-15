package ru.adel.if_else_task_2.public_interface.authentication;

import jakarta.validation.Valid;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginResponseDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationResponseDto;

public interface AuthenticationService {

    RegistrationResponseDto registrate(@Valid RegistrationRequestDto registrationRequestDto);

    LoginResponseDto login(@Valid LoginRequestDto loginRequestDto);
}
