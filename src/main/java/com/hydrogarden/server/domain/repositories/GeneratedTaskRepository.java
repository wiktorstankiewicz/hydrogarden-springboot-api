package com.hydrogarden.server.domain.repositories;

import com.hydrogarden.server.domain.entities.GeneratedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedTaskRepository extends JpaRepository<GeneratedTask, Integer> {
    void deleteByDone(boolean done);

   /* @Query("SELECT g FROM GeneratedTask g JOIN FETCH g.user, g.circuit WHERE g.id = (:id)")
    List<GeneratedTask> findAllAndFetchUserAAndCircuit();*/
}
