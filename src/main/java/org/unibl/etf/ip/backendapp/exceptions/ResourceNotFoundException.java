package org.unibl.etf.ip.backendapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException()
    {
        super("Resource not found");
    }
}