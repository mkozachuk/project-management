package com.mkozachuk.projectmanagement.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id) {
        super("This employee doesn't exist! id: " + id);
    }
}
