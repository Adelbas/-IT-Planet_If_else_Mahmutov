package ru.adel.if_else_task_2.public_interface.account;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountResponseDto;
import ru.adel.if_else_task_2.public_interface.account.dto.AccountSearchRequestDto;
import ru.adel.if_else_task_2.public_interface.account.dto.UpdateAccountRequestDto;
import ru.adel.model.AccountResponse;
import ru.adel.model.UpdateAccountRequest;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountMapper {

    AccountResponseDto toAccountResponseDto(Client client);

    AccountResponse toAccountResponse(AccountResponseDto accountResponseDto);

    UpdateAccountRequestDto toUpdateAccountRequestDto(Long id, UpdateAccountRequest updateAccountRequest);

    AccountSearchRequestDto toAccountSearchRequestDto(
            Optional<String> firstName, Optional<String> lastName,
            Optional<String> email, int form, int size
    );

    default String optionalToString (Optional<String> optional) {
        return optional.orElse(null);
    }

    List<AccountResponse> toAccountResponses(List<AccountResponseDto> accountResponseDtos);

    List<AccountResponseDto> toAccountResponseDtos(List<Client> clients);
}
