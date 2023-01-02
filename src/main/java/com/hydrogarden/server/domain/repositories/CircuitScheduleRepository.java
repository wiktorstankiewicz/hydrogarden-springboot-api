package com.hydrogarden.server.domain.repositories;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitScheduleRepository extends JpaRepository<CircuitSchedule, Integer> {
    List<CircuitSchedule> findByCircuit(Circuit circuit);
}
