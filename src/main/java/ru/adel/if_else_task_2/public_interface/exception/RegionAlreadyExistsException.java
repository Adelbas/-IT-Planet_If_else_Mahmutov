package ru.adel.if_else_task_2.public_interface.exception;

public class RegionAlreadyExistsException extends RuntimeException{
    public RegionAlreadyExistsException(Double latitude, Double longitude) {
        super("Region with latitude: " + latitude + " longitude: " + longitude + " already exists");
    }
}
