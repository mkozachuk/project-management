package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/{id}/assign-to-client/{clientId}")
    Project assignProjectToClient(@PathVariable("id") Long id, @PathVariable("clientId") Long clientId) {
        return projectService.assignProjectToClient(clientService.findById(clientId), projectService.findById(id));
    }

    @GetMapping("/{id}/assign-to-employee/{employeeId}")
    Project assignProjectToEmployee(@PathVariable("id") Long id, @PathVariable("employeeId") Long employeeId) {
        return projectService.assignProjectToEmployee(employeeService.findById(employeeId), projectService.findById(id));
    }

}
