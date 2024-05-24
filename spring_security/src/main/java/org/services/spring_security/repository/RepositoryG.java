package org.services.spring_security.repository;

import org.services.spring_security.entities.Grand_Type;
import org.services.spring_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryG extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
