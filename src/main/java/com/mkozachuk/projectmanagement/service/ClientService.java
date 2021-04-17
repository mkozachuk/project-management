package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.exception.ClientNotFoundException;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }


    public Client findById(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id)) ;
    }
}
