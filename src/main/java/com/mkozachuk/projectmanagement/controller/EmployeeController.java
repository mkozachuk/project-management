package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    Employee createNewEmployee(@RequestBody Employee newEmployee) {
        return employeeService.save(newEmployee);
    }

}
