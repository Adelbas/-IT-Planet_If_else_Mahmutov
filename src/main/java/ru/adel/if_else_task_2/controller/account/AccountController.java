package ru.adel.if_else_task_2.controller.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.if_else_task_2.api.AccountApi;
import ru.adel.if_else_task_2.public_interface.account.AccountMapper;
import ru.adel.if_else_task_2.public_interface.account.AccountService;
import ru.adel.if_else_task_2.public_interface.account.dto.UpdateAccountRequestDto;
import ru.adel.if_else_task_2.model.AccountResponse;
import ru.adel.if_else_task_2.model.UpdateAccountRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<AccountResponse> getAccount(Long accountId) {
        log.info("Handle get account request: {}", accountId);

        AccountResponse accountResponse = accountMapper.toAccountResponse(accountService.getClientAccount(accountId));

        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccount(Long accountId, UpdateAccountRequest updateAccountRequest) {
        log.info("Handle update account request: {}", accountId);

        UpdateAccountRequestDto updateAccountRequestDto = accountMapper.toUpdateAccountRequestDto(accountId, updateAccountRequest);

        AccountResponse accountResponse = accountMapper.toAccountResponse(accountService.updateClientAccount(updateAccountRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Long accountId) {
        log.info("Handle delete account request: {}", accountId);

        accountService.deleteAccount(accountId);

        return ResponseEntity.ok().build();
    }
}
