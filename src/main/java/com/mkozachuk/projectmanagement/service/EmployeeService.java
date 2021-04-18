package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.exception.EmployeeNotFoundException;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

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

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }

    public Employee update(Long id, Employee newEmployee){
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setEmail(newEmployee.getEmail());
                    employee.setPesel(newEmployee.getPesel());
                    employee.setProjects(newEmployee.getProjects());
                    return save(newEmployee);
                })
                .orElseGet(() -> {
                    newEmployee.setEmployeeId(id);
                    return save(newEmployee);
                });
    }

    public List<Employee> saveAll(List<Employee> employees){
        return employeeRepository.saveAll(employees);
    }
}
