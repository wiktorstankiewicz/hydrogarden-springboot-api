package com.hydrogarden.server.domain.repositories;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Integer> {
    List<Circuit> findByUser(User user);
    Optional<Circuit> findCircuitByCodeAndUser(int code, User user);
}

