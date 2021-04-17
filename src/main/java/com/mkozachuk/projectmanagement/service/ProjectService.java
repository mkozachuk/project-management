package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

}
