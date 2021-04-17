package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private static String baseUrl;

    @BeforeAll
    public static void setUp(){
        baseUrl = "/api/v1/project";
    }

    @Test
    public void testCreateNewProject() throws Exception {
        Project project = new Project("awesomeProject", new Date(), new Date());
        Mockito.when(projectService.save(project)).thenReturn(project);

        MvcResult result = mockMvc.perform(post(baseUrl).contentType("application/json")
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
        Project project = new Project("",new Date(), new Date());

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

    @Test
    public void testStartDateMustBeNotNull() throws Exception {
        Project project = new Project("awesomeProject",null, new Date());

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

    @Test
    public void testFinishDateMustBeNotNull() throws Exception {
        Project project = new Project("awesomeProject",new Date(), null);

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(projectService, Mockito.times(0)).save(project);
    }

}