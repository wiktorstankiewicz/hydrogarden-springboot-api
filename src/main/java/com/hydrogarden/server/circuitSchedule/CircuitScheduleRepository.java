package com.hydrogarden.server.circuitSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircuitScheduleRepository extends JpaRepository<CircuitSchedule,Integer> {
}
