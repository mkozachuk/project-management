package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Employee;
import com.mkozachuk.projectmanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

}