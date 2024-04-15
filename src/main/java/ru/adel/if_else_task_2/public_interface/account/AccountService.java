package ru.adel.if_else_task_2.public_interface.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountResponseDto;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountSearchRequestDto;
import ru.adel.if_else_task_2.public_interface.account.dto.UpdateAccountRequestDto;

import java.util.List;

public interface AccountService {

    AccountResponseDto getClientAccount(@Min(1) Long id);

    AccountResponseDto updateClientAccount(@Valid UpdateAccountRequestDto updateAccountRequestDto);

    void deleteAccount(@Min(1) Long accountId);

    List<AccountResponseDto> searchAccounts(AccountSearchRequestDto accountSearchRequestDto);
}
