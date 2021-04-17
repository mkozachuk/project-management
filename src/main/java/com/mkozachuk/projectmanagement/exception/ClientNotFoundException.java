package com.mkozachuk.projectmanagement.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("This client doesn't exist! id: " + id);
    }
}
