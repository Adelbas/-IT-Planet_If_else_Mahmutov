package ru.adel.if_else_task_2.core.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.entity.Client;
import ru.adel.if_else_task_2.core.repository.ClientRepository;
import ru.adel.if_else_task_2.public_interface.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientDbService {

    private final ClientRepository clientRepository;

    public Optional<Client> findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client saveClient(Client client) {
        log.info("Saving client to db: {}", client);
        return clientRepository.save(client);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Client not found"));
    }

    public void deleteAccount(Client client) {
        log.info("Deleting account from db: {}", client);
        clientRepository.delete(client);
    }

    public List<Client> search(String firstName, String lastName, String email, int form, int size) {
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (email == null) {
            email = "";
        }
        return clientRepository.findClientByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                firstName, lastName, email, PageRequest.of(form,size)
        ).get().collect(Collectors.toList());
    }
}
