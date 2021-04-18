package com.mkozachuk.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkozachuk.projectmanagement.exception.ClientNotFoundException;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.service.ClientService;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    private static String baseUrl;

    private Client client;

    @BeforeAll
    public static void setUp() {
        baseUrl = "/api/v1/clients";
    }

    @BeforeEach
    public void createClient() {
        client = new Client("SpaceX");
    }

    @Test

    public void testCreateNewClient() throws Exception {

        clientService.save(client);

        MvcResult result = mockMvc.perform(post(baseUrl).contentType("application/json")
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("create-new-client"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(client);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }


    @Test
    public void testFindAll() throws Exception {
        List<Client> allClients = new ArrayList<>();
        allClients.add(new Client("SpaceX"));
        allClients.add(new Client("Tesla"));
        allClients.add(new Client("StarLink"));
        allClients.add(new Client("Nasa"));
        clientService.saveAll(allClients);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-clients"))
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(allClients);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testDeleteClient() throws Exception {
        Long clientId = 1L;
        clientService.save(client);
        String url = baseUrl + "/" + clientId;

        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-client"));

        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.findById(clientId);
        });
    }

    @Test
    public void testUpdateClient() throws Exception {
        Long clientId = 1L;
        clientService.save(client);
        Client newClient = client;
        newClient.setCompanyName("newCompanyName");
        String url = baseUrl + "/" + clientId;

        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newClient)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-client"))
                .andReturn();

        String actualJsonResponse = result.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(newClient);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expected);
    }

}