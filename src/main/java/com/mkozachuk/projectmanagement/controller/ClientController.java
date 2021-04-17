package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Client createNewClient(@Valid @RequestBody Client newClient) {
        return clientService.save(newClient);
    }
}
