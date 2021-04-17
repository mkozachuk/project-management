package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ProjectService projectService;

    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/employees";
    }

    @Test
    public void testCreateNewEmployee() throws Exception {
        Employee employee = new Employee("John", "Doe","jonh.doe@company.com","12345678901");

        Mockito.when(employeeService.save(employee)).thenReturn(employee);

        MvcResult result = mockMvc.perform(post(baseUrl).contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

        Mockito.verify(employeeService, Mockito.times(1)).save(employee);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(employee);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testFirstNameMustBeNotBlank() throws Exception {
        Employee employee = new Employee("", "Doe","jonh.doe@company.com","12345678901");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testLastNameMustBeNotBlank() throws Exception {
        Employee employee = new Employee("John", "","jonh.doe@company.com","12345678901");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testPeselMustBeNotBlank() throws Exception {
        Employee employee = new Employee("John", "Doe","jonh.doe@company.com","");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testEmailMustBeNotBlank() throws Exception {
        Employee employee = new Employee("John", "Doe","","12345678901");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testEmailMustBeEmailStyle() throws Exception {
        Employee employee = new Employee("John", "Doe","fakeEmail","12345678901");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testAssignEmployeeToProject() throws Exception {
        Long employeeId = 1L;
        Long projectId = 1L;

        Employee employee = new Employee("John", "Doe","jonh.doe@company.com","12345678901");
        Project project = new Project("CoolProject", new Date(), new Date());
        String url = baseUrl + "/" + employeeId + "/assign/" + projectId;

        Employee employeeWithSignedProject = employee;
        employeeWithSignedProject.getProjects().add(project);

        Mockito.when(employeeService.findById(employeeId)).thenReturn(employee);
        Mockito.when(projectService.findById(projectId)).thenReturn(project);
        Mockito.when(employeeService.assignEmployeeToProject(employee,project)).thenReturn(employeeWithSignedProject);

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