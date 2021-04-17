package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.service.ClientService;
import com.mkozachuk.projectmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

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
        Project project = new Project("CoolProject", new Date(), new Date());
        projectService.save(project);

        Project assignedProject = project;
        assignedProject.setClient(client);

        String url = baseUrl + "/" + projectId + "/assign/" + clientId;

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