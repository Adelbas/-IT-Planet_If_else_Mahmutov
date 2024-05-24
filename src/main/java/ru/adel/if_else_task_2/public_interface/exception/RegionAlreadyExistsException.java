package ru.adel.if_else_task_2.public_interface.exception;

public class RegionAlreadyExistsException extends RuntimeException {
    public RegionAlreadyExistsException(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        super("Region with latitude1: " + latitude1 + " longitude1: " + longitude1 + " latitude2: " + latitude2 + " longitude2: " + longitude2 + " already exists!");
    }
}
