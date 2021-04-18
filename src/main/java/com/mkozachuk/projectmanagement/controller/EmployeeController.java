package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private ProjectService projectService;

    @Autowired
    EmployeeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostMapping
    Employee createNewEmployee(@Valid @RequestBody Employee newEmployee) {
        return employeeService.save(newEmployee);
    }

    @GetMapping("/{id}/assign/{projectId}")
    Employee assignEmployeeToProject(@PathVariable("id") Long id, @PathVariable("projectId") Long projectId) {
        return employeeService.assignEmployeeToProject(employeeService.findById(id), projectService.findById(projectId));
    }

    @GetMapping
    List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    Employee findById(@PathVariable("id") Long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Employee update(@PathVariable("id") Long id, @Valid @RequestBody Employee employee) {
        return employeeService.update(id,employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
    }

}
