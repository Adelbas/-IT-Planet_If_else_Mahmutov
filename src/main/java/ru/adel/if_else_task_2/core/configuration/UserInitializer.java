package ru.adel.if_else_task_2.core.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.service.db.ClientDbService;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private static final Long DEFAULT_CHECK_ID = 1L;

    private final ClientDbService clientDbService;

    @PostConstruct
    public void init() {
        if (!clientWithIdExists(DEFAULT_CHECK_ID)) {
            clientDbService.saveClient(
                    Client.builder()
                            .firstName("Ivan")
                            .lastName("Ivanov")
                            .email("ivan@mail.ru")
                            .password("ivan")
                            .build()
            );
        }
    }

    private boolean clientWithIdExists(Long id) {
        return clientDbService.findClientById(id).isPresent();
    }
}
