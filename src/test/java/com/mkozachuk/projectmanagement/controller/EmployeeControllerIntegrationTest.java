package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.exception.EmployeeNotFoundException;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
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

    private Employee employee;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/employees";
    }

    @BeforeEach
    public void createNewEmployee() {
        employee = new Employee("John", "Doe", "jonh.doe@company.com", "12345678901");
    }


    @Test
    public void testAssignEmployeeToProject() throws Exception {
        Long employeeId = 1L;
        Long projectId = 1L;

        Set<Project> projects = new HashSet<>();

        employeeService.save(employee);
        Project project = new Project("CoolProject", new Date(), new Date(), null, new HashSet<>());
        projectService.save(project);
        String url = baseUrl + "/" + employeeId + "/assign/" + projectId;

        Employee employeeWithSignedProject = employee;
        projects.add(project);
        employeeWithSignedProject.setProjects(projects);

        MvcResult result = mockMvc.perform(get(url)
                .contentType("application/json"))
                .andDo(print())
                .andDo(document("assign-employee-to-project"))
                .andExpect(status().isOk())
                .andReturn();
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(employeeWithSignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testCreateNewEmployee() throws Exception {

        Employee expectedEmployee = employee;
        expectedEmployee.setEmployeeId(1L);

        MvcResult result = mockMvc.perform(post(baseUrl).contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("create-new-employee"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(expectedEmployee);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(new Employee("John", "Doe", "jonh.doe@company.com", "12345678901", new HashSet<>()));
        allEmployees.add(new Employee("Albert", "Doe", "albert.doe@company.com", "12345678902", new HashSet<>()));
        allEmployees.add(new Employee("Jane", "Doe", "jane.doe@company.com", "12345678903", new HashSet<>()));
        employeeService.saveAll(allEmployees);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-employees"))
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allEmployees);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        employeeService.save(employee);

        Long employeeId = 1L;
        String url = baseUrl + "/" + employeeId;

        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andDo(document("delete-employee"));

        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.findById(employeeId);
        });

    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        employeeService.save(employee);

        Employee newEmployee = employee;
        newEmployee.setFirstName("changedFirstName");
        String url = baseUrl + "/" + employeeId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-employee"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newEmployee);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}