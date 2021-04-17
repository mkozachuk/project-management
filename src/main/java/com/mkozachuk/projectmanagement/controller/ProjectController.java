package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    private ProjectService projectService;
    private ClientService clientService;

    @Autowired
    public ProjectController(ProjectService projectService, ClientService clientService) {
        this.projectService = projectService;
        this.clientService = clientService;
    }

    @PostMapping
    Project createNewProject(@Valid @RequestBody Project newProject) {
        return projectService.save(newProject);
    }

    @GetMapping("/{id}/assign/{clientId}")
    Project assignProjectToClient(@PathVariable("id") Long id, @PathVariable("clientId") Long clientId) {
        return projectService.assignProjectToClient(clientService.findById(clientId), projectService.findById(id));
    }

}
