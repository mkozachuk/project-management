package com.mkozachuk.projectmanagement.repository;

import com.mkozachuk.projectmanagement.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateClient() {

        Client client = clientRepository.save(new Client("SpaceX"));

        assertThat(client).isNotNull();
        assertThat(client.getClientId()).isGreaterThan(0);
    }

}