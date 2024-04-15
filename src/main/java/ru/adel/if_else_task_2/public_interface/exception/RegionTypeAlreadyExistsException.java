package ru.adel.if_else_task_2.public_interface.exception;

public class RegionTypeAlreadyExistsException extends RuntimeException{
    public RegionTypeAlreadyExistsException(String type) {
        super("Region type " + type + " already exists");
    }
}
