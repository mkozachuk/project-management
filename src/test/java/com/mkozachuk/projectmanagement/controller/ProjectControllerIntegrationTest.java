package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.exception.ProjectNotFoundException;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    private static String baseUrl;

    private Project project;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/projects";
    }

    @BeforeEach
    public void createNewProject() {
        project = new Project("awesomeProject", new Date(), new Date(), null, new HashSet<>());
    }


    @Test
    public void testAssignProjectToClient() throws Exception {
        Long clientId = 1L;
        Long projectId = 1L;

        Client client = new Client("SpaceX");
        clientService.save(client);

        Project assignedProject = projectService.save(project);
        assignedProject.setClient(client);

        String url = baseUrl + "/" + projectId + "/assign-to-client/" + clientId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType("application/json"))
                .andDo(print())
                .andDo(document("assign-project-to-client"))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(assignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testAssignProjectToEmployee() throws Exception {
        Long employeeId = 1L;
        Long projectId = 1L;

        Set<Employee> employees = new HashSet<>();
        Employee employee = new Employee("John", "Doe", "jonh.doe@company.com", "12345678901");
        employeeService.save(employee);

        Project assignedProject = projectService.save(project);
        ;
        employees.add(employee);
        assignedProject.setEmployees(employees);

        String url = baseUrl + "/" + projectId + "/assign-to-employee/" + employeeId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType("application/json"))
                .andDo(print())
                .andDo(document("assign-project-to-employee"))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(assignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testCreateNewProject() throws Exception {
        Project expectedProject = project;
        expectedProject.setProjectId(1L);
        MvcResult result = mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("create-new-project"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(project);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);

    }

    @Test
    public void testFindAll() throws Exception {
        List<Project> allProjects = new ArrayList<>();
        allProjects.add(new Project("awesomeProject", new Date(), new Date()));
        allProjects.add(new Project("anotherAwesomeProject", new Date(), new Date()));
        allProjects.add(new Project("oneMoreAwesomeProject", new Date(), new Date()));
        projectService.saveAll(allProjects);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-projets"))
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allProjects);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteProject() throws Exception {
        projectService.save(project);
        Long projectId = 1L;
        String url = baseUrl + "/" + projectId;

        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andDo(document("delete-project"));

        Assertions.assertThrows(ProjectNotFoundException.class, () -> {
            projectService.findById(projectId);
        });
    }

    @Test
    public void testUpdateProject() throws Exception {
        Long projectId = 1L;
        projectService.save(project);

        Project newProject = project;
        newProject.setProjectName("newProjectName");
        String url = baseUrl + "/" + projectId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProject)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-project"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newProject);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}