package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/projects";
    }

    @Test
    public void testAssignProjectToClient() throws Exception {
        Long clientId = 1L;
        Long projectId = 1L;

        Client client = new Client("SpaceX");
        clientService.save(client);
        Project project = new Project("CoolProject", new Date(), new Date(), null, new HashSet<>());


        Project assignedProject = projectService.save(project);
        assignedProject.setClient(client);

        String url = baseUrl + "/" + projectId + "/assign-to-client/" + clientId;

        MvcResult result = mockMvc.perform(get(url)
                .contentType("application/json"))
                .andDo(print())
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
        Project project = new Project("CoolProject", new Date(), new Date());
        projectService.save(project);

        Project assignedProject = project;
        employees.add(employee);
        assignedProject.setEmployees(employees);
        projectService.save(assignedProject);

        String url = baseUrl + "/" + projectId + "/assign-to-employee/" + employeeId;

        MvcResult result = mockMvc.perform(get(url)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(assignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}