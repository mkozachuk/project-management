package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testCreateNewEmployee() throws Exception {
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
        employee.setFirstName("");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testLastNameMustBeNotBlank() throws Exception {
        employee.setLastName("");
        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testPeselMustBeNotBlank() throws Exception {
        employee.setPesel("");
        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testEmailMustBeNotBlank() throws Exception {
        employee.setEmail("");
        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(0)).save(employee);
    }

    @Test
    public void testEmailMustBeEmailStyle() throws Exception {
        employee.setEmail("fakeEmail");
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

        Set<Project> projects = new HashSet<>();
        Project project = new Project("CoolProject", new Date(), new Date());
        String url = baseUrl + "/" + employeeId + "/assign/" + projectId;

        Employee employeeWithSignedProject = employee;
        projects.add(project);
        employeeWithSignedProject.setProjects(projects);

        Mockito.when(employeeService.findById(employeeId)).thenReturn(employee);
        Mockito.when(projectService.findById(projectId)).thenReturn(project);
        Mockito.when(employeeService.assignEmployeeToProject(employee, project)).thenReturn(employeeWithSignedProject);

        MvcResult result = mockMvc.perform(get(url)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(employeeWithSignedProject);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(new Employee("John", "Doe", "jonh.doe@company.com", "12345678901"));
        allEmployees.add(new Employee("Albert", "Doe", "albert.doe@company.com", "12345678902"));
        allEmployees.add(new Employee("Jane", "Doe", "jane.doe@company.com", "12345678903"));
        Mockito.when(employeeService.findAll()).thenReturn(allEmployees);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allEmployees);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        Mockito.doNothing().when(employeeService).deleteById(employeeId);
        String url = baseUrl + "/" + employeeId;

        mockMvc.perform(delete(url)).andExpect(status().isOk());

        Mockito.verify(employeeService, Mockito.times(1)).deleteById(employeeId);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        Employee newEmployee = employee;
        Mockito.when(employeeService.update(employeeId, newEmployee)).thenReturn(newEmployee);
        String url = baseUrl + "/" + employeeId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Mockito.verify(employeeService, Mockito.times(1)).update(employeeId, newEmployee);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newEmployee);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}