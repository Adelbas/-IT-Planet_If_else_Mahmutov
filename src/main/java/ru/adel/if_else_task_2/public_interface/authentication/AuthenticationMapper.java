package ru.adel.if_else_task_2.public_interface.authentication;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginResponseDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationResponseDto;
import ru.adel.if_else_task_2.model.RegistrationRequest;
import ru.adel.if_else_task_2.model.RegistrationResponse;
import ru.adel.if_else_task_2.model.LoginRequest;
import ru.adel.if_else_task_2.model.LoginResponse;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthenticationMapper {

    RegistrationRequestDto toRegistrationRequestDto(RegistrationRequest registrationRequest);

    RegistrationResponse toRegistrationResponse(RegistrationResponseDto registrationResponseDto);

    LoginRequestDto toLoginRequestDto(LoginRequest loginRequest);

    LoginResponse toLoginResponse(LoginResponseDto loginResponseDto);
}
