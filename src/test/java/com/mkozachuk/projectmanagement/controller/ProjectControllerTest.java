package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ClientService clientService;

    @MockBean
    private EmployeeService employeeService;

    private static String baseUrl;

    private Project project;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/projects";
    }

    @BeforeEach
    public void createNewProject() {
        project = new Project("awesomeProject", new Date(), new Date(),null);
    }


    @Test
    public void testCreateNewProject() throws Exception {
        Mockito.when(projectService.save(project)).thenReturn(project);

        MvcResult result = mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

        Mockito.verify(projectService, Mockito.times(1)).save(project);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(project);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);

    }

    @Test
    public void testProjectNameMustBeNotBlank() throws Exception {
        project.setProjectName("");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

    @Test
    public void testStartDateMustBeNotNull() throws Exception {
        project.setStartDate(null);

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

    @Test
    public void testFinishDateMustBeNotNull() throws Exception {
        project.setFinishDate(null);

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Project> allProjects = new ArrayList<>();
        allProjects.add(new Project("awesomeProject", new Date(), new Date()));
        allProjects.add(new Project("anotherAwesomeProject", new Date(), new Date()));
        allProjects.add(new Project("oneMoreAwesomeProject", new Date(), new Date()));
        Mockito.when(projectService.findAll()).thenReturn(allProjects);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allProjects);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteProject() throws Exception {
        Long projectId = 1L;
        Mockito.doNothing().when(projectService).deleteById(projectId);
        String url = baseUrl + "/" + projectId;

        mockMvc.perform(delete(url)).andExpect(status().isOk());

        Mockito.verify(projectService, Mockito.times(1)).deleteById(projectId);
    }

    @Test
    public void testUpdateProject() throws Exception {
        Long projectId = 1L;
        Project newProject = project;
        Mockito.when(projectService.update(projectId, newProject)).thenReturn(newProject);
        String url = baseUrl + "/" + projectId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProject)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Mockito.verify(projectService, Mockito.times(1)).update(projectId, newProject);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newProject);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}