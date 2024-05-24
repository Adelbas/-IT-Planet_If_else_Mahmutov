package ru.adel.if_else_task_2.core.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.service.db.ClientDbService;
import ru.adel.if_else_task_2.public_interface.authentication.AuthenticationService;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.LoginResponseDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationRequestDto;
import ru.adel.if_else_task_2.public_interface.authentication.dto.RegistrationResponseDto;
import ru.adel.if_else_task_2.public_interface.exception.AlreadyAuthenticatedException;
import ru.adel.if_else_task_2.public_interface.exception.ClientAlreadyExistsException;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientDbService clientDbService;

    @Override
    public RegistrationResponseDto registrate(@Valid RegistrationRequestDto registrationRequestDto) {
        if (SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            throw new AlreadyAuthenticatedException("Request from authenticated account");
        }
        Optional<Client> client = clientDbService.findClientByEmail(registrationRequestDto.email());

        if (client.isPresent()) {
            throw new ClientAlreadyExistsException(registrationRequestDto.email());
        }

        Client createdClient = clientDbService.saveClient(Client.builder().email(registrationRequestDto.email()).firstName(registrationRequestDto.firstName()).lastName(registrationRequestDto.lastName()).password(registrationRequestDto.password()).build());

        return RegistrationResponseDto.builder().id(createdClient.getId()).email(createdClient.getEmail()).firstName(createdClient.getFirstName()).lastName(createdClient.getLastName()).build();
    }

    @Override
    public LoginResponseDto login(@Valid LoginRequestDto loginRequestDto) {
        Client client = clientDbService.findClientByEmail(loginRequestDto.email())
                .orElseThrow(() -> new BadCredentialsException("Incorrect email or password"));

        if (!client.getPassword().equals(loginRequestDto.password())) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        return LoginResponseDto.builder().id(client.getId()).build();
    }
}
