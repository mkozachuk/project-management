package com.mkozachuk.projectmanagement.repository;

import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProject() {
        Client client = entityManager.persist(new Client("SpaceX"));

        Project project = projectRepository.save(new Project("awesomeProject", new Date(), new Date(), client));

        assertThat(project).isNotNull();
        assertThat(project.getProjectId()).isGreaterThan(0);
    }

}