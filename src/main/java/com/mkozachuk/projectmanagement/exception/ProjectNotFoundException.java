package com.mkozachuk.projectmanagement.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id) {
        super("This project doesn't exist! id: " + id);
    }
}
