package com.takdelivery.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " con id " + id + " no fue encontrado");
    }

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
