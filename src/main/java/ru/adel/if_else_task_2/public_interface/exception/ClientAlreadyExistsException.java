package ru.adel.if_else_task_2.public_interface.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException(String email) {
        super("Client " + email + " already exists");
    }
}
