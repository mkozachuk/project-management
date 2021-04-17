package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/employees";
    }

    @Test
    public void testAssignEmployeeToProject() throws Exception {
        Long employeeId = 1L;
        Long projectId = 1L;

        Employee employee = new Employee("John", "Doe","jonh.doe@company.com","12345678901");
        employeeService.save(employee);
        Project project = new Project("CoolProject", new Date(), new Date());
        projectService.save(project);
        String url = baseUrl + "/" + employeeId + "/assign/" + projectId;

        Employee employeeWithSignedProject = employee;
        employeeWithSignedProject.getProjects().add(project);

        MvcResult result = mockMvc.perform(get(url)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(employeeWithSignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}