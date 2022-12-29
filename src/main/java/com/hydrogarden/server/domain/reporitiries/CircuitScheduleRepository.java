package com.hydrogarden.server.domain.reporitiries;

import com.hydrogarden.server.domain.entities.CircuitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircuitScheduleRepository extends JpaRepository<CircuitSchedule, Integer> {
}
