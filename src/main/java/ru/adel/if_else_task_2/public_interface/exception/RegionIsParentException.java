package ru.adel.if_else_task_2.public_interface.exception;

public class RegionIsParentException extends RuntimeException{
    public RegionIsParentException(Long id) {
        super("Region with id " + id + " is parent for other regions");
    }
}
