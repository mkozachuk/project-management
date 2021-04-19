package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    private ProjectService projectService;
    private ClientService clientService;
    private EmployeeService employeeService;

    @Autowired
    public ProjectController(ProjectService projectService, ClientService clientService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    @PostMapping
    Project createNewProject(@Valid @RequestBody Project newProject) {
        return projectService.save(newProject);
    }

    @PutMapping("/{id}/assign-to-client/{clientId}")
    Project assignProjectToClient(@PathVariable("id") Long id, @PathVariable("clientId") Long clientId) {
        return projectService.assignProjectToClient(clientService.findById(clientId), projectService.findById(id));
    }

    @PutMapping("/{id}/assign-to-employee/{employeeId}")
    Project assignProjectToEmployee(@PathVariable("id") Long id, @PathVariable("employeeId") Long employeeId) {
        return projectService.assignProjectToEmployee(employeeService.findById(employeeId), projectService.findById(id));
    }

    @GetMapping
    List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    Project findById(@PathVariable("id") Long id) {
        return projectService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Project update(@PathVariable("id") Long id, @Valid @RequestBody Project project) {
        return projectService.update(id,project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") Long id) {
        projectService.deleteById(id);
    }

}
