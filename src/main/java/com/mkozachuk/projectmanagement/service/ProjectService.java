package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.exception.ProjectNotFoundException;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project assignProjectToClient(Client client, Project project) {
        client.getProjects().add(project);
        project.setClient(client);
        return save(project);
    }

    public Project assignProjectToEmployee(Employee employee, Project project) {
        if (project.getEmployees() == null) {
            project.setEmployees(new HashSet<>());
        }
        project.getEmployees().add(employee);
        employee.getProjects().add(project);
        return save(project);
    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public void deleteById(Long id){
        projectRepository.deleteById(id);
    }

    public Project update(Long id, Project newProject){
        return projectRepository.findById(id)
                .map(project -> {
                    project.setProjectName(newProject.getProjectName());
                    project.setStartDate(newProject.getStartDate());
                    project.setFinishDate(newProject.getFinishDate());
                    project.setClient(newProject.getClient());
                    project.setEmployees(newProject.getEmployees());
                    return save(newProject);
                })
                .orElseGet(() -> {
                    newProject.setProjectId(id);
                    return save(newProject);
                });
    }

}
