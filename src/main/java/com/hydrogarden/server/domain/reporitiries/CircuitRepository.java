package com.hydrogarden.server.domain.reporitiries;

import com.hydrogarden.server.domain.entities.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit,Integer> {
}

