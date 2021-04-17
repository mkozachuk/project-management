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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void testFindAll() throws Exception {
        List<Client> allClients = new ArrayList<>();
        allClients.add(new Client( "SpaceX"));
        allClients.add(new Client( "Tesla"));
        allClients.add(new Client( "StarLink"));
        allClients.add(new Client( "Nasa"));
        Mockito.when(clientService.findAll()).thenReturn(allClients);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allClients);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteClient() throws Exception {
        Long clientId = 1L;
        Mockito.doNothing().when(clientService).deleteById(clientId);
        String url = baseUrl +"/"+ clientId;

        mockMvc.perform(delete(url)).andExpect(status().isOk());

        Mockito.verify(clientService, Mockito.times(1)).deleteById(clientId);
    }

    @Test
    public void testUpdateClient() throws Exception{
        Long clientId = 1L;
        Client newClient = new Client("Apple");
        Mockito.when(clientService.update(clientId,newClient)).thenReturn(newClient);
        String url = baseUrl +"/"+ clientId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newClient)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Mockito.verify(clientService, Mockito.times(1)).update(clientId,newClient);
        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newClient);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}