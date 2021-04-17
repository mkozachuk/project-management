package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.service.ClientService;
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

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/clients";
    }

    @Test
    public void testCreateNewClient() throws Exception {
        Client client = new Client("SpaceX");

        Mockito.when(clientService.save(client)).thenReturn(client);

        MvcResult result = mockMvc.perform(post(baseUrl).contentType("application/json")
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

        Mockito.verify(clientService, Mockito.times(1)).save(client);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(client);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testCompanyNameMustBeNotBlank() throws Exception {
        Client client = new Client("");

        mockMvc.perform(post(baseUrl)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        Mockito.verify(clientService, Mockito.times(0)).save(client);
    }

}