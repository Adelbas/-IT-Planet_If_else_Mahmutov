package ru.adel.if_else_task_2.public_interface.exception;

public class RegionWithTypeExistsException extends RuntimeException {
    public RegionWithTypeExistsException(String type) {
        super("Regions with type " + type + " still exists");
    }
}
