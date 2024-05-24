package ru.adel.if_else_task_2.controller.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.if_else_task_2.api.AccountSearchApi;
import ru.adel.if_else_task_2.public_interface.account.AccountMapper;
import ru.adel.if_else_task_2.public_interface.account.AccountService;
import ru.adel.if_else_task_2.model.AccountResponse;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountResponseDto;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountSearchRequestDto;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountSearchController implements AccountSearchApi {

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_FORM = 0;

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<List<AccountResponse>> searchAccount(Optional<String> firstName, Optional<String> lastName, Optional<String> email, Optional<Integer> form, Optional<Integer> size) {
        log.info("Handle account search request");

        AccountSearchRequestDto accountSearchRequestDto = accountMapper.toAccountSearchRequestDto(
                firstName,
                lastName,
                email,
                form.orElse(DEFAULT_FORM),
                size.orElse(DEFAULT_SIZE)
        );

        log.info(accountSearchRequestDto.toString());
        List<AccountResponseDto> accountResponseDtos = accountService.searchAccounts(accountSearchRequestDto);

        List<AccountResponse> accountResponses = accountMapper.toAccountResponses(accountResponseDtos);

        return ResponseEntity.status(HttpStatus.OK).body(accountResponses);
    }

}
