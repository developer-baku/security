package org.services.spring_security.repository;

import org.services.spring_security.entities.Grand_Type;
import org.services.spring_security.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RepositoryS extends JpaRepository< Scope,Long> {
}
