package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.exception.EmployeeNotFoundException;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee assignEmployeeToProject(Employee employee, Project project) {
        if (employee.getProjects() == null) {
            employee.setProjects(new HashSet<>());
        }
        employee.getProjects().add(project);
        return save(employee);
    }
}
