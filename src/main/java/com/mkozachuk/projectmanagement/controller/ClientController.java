package com.mkozachuk.projectmanagement.controller;

import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
class ClientController {
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

    @GetMapping
    List<Client> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    Client findById(@PathVariable("id") Long id) {
        return clientService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Client update(@PathVariable("id") Long id, @RequestBody Client client) {
        return clientService.update(id,client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        clientService.deleteById(id);
    }
}
