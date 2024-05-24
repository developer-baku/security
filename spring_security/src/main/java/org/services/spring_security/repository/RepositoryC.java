package org.services.spring_security.repository;

import org.services.spring_security.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryC extends JpaRepository<Client,Long > {
    Optional<Client> findByClientId(String clientId);

}
