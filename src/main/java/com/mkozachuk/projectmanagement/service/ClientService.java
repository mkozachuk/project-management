package com.mkozachuk.projectmanagement.service;

import com.mkozachuk.projectmanagement.exception.ClientNotFoundException;
import com.mkozachuk.projectmanagement.model.Client;
import com.mkozachuk.projectmanagement.model.Project;
import com.mkozachuk.projectmanagement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public void deleteById(Long id){
        clientRepository.deleteById(id);
    }

    public Client update(Long id, Client newClient){
        return clientRepository.findById(id)
                .map(client -> {
                    client.setCompanyName(newClient.getCompanyName());
                    client.setProjects(newClient.getProjects());
                    return save(client);
                })
                .orElseGet(() -> {
                    newClient.setClientId(id);
                    return save(newClient);
                });
    }
}
