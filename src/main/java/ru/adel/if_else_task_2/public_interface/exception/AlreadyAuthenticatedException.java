package ru.adel.if_else_task_2.public_interface.exception;

public class AlreadyAuthenticatedException extends RuntimeException{
    public AlreadyAuthenticatedException(String message) {
        super(message);
    }
}
