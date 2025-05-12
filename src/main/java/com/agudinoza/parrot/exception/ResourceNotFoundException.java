package com.agudinoza.parrot.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("No se encontró %s con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}