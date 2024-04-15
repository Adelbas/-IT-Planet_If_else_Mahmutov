package ru.adel.if_else_task_2.core.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.service.db.ClientDbService;
import ru.adel.if_else_task_2.public_interface.account.AccountMapper;
import ru.adel.if_else_task_2.public_interface.account.AccountService;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountResponseDto;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountSearchRequestDto;
import ru.adel.if_else_task_2.public_interface.account.dto.UpdateAccountRequestDto;
import ru.adel.if_else_task_2.public_interface.exception.ClientAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final ClientDbService clientDbService;

    @Override
    public AccountResponseDto getClientAccount(@Min(1) Long id) {
        return accountMapper.toAccountResponseDto(
                clientDbService.getClientById(id)
        );
    }

    @Override
    public AccountResponseDto updateClientAccount(@Valid UpdateAccountRequestDto updateAccountRequestDto) {
        Long cookieId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!cookieId.equals(updateAccountRequestDto.id())) {
            throw new AccessDeniedException("Updating non-personal account");
        }

        Client client = clientDbService.findClientById(updateAccountRequestDto.id())
                .orElseThrow(()->new AccessDeniedException("Account not found"));

        Optional<Client> sameEmailClient = clientDbService.findClientByEmail(updateAccountRequestDto.email());
        if (sameEmailClient.isPresent() && !cookieId.equals(sameEmailClient.get().getId())) {
            throw new ClientAlreadyExistsException(updateAccountRequestDto.email());
        }

        client.setEmail(updateAccountRequestDto.email());
        client.setFirstName(updateAccountRequestDto.firstName());
        client.setLastName(updateAccountRequestDto.lastName());
        client.setPassword(updateAccountRequestDto.password());

        return accountMapper.toAccountResponseDto(clientDbService.saveClient(client));
    }

    @Override
    public void deleteAccount(@Min(1) Long accountId) {
        Long cookieId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!cookieId.equals(accountId)) {
            throw new AccessDeniedException("Deleting non-personal account");
        }

        Client client = clientDbService.findClientById(accountId)
                .orElseThrow(()->new AccessDeniedException("Account not found"));

        clientDbService.deleteAccount(client);
    }

    @Override
    public List<AccountResponseDto> searchAccounts(AccountSearchRequestDto accountSearchRequestDto) {
        List<Client> clients = clientDbService.search(
                accountSearchRequestDto.firstName(),
                accountSearchRequestDto.lastName(),
                accountSearchRequestDto.email(),
                accountSearchRequestDto.form(),
                accountSearchRequestDto.size()
        );

        return accountMapper.toAccountResponseDtos(clients);
    }
}
