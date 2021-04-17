package com.mkozachuk.projectmanagement.repository;

import com.mkozachuk.projectmanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
