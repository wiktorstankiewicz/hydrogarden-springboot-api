package com.hydrogarden.server.domain.repositories;

import com.hydrogarden.server.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean findByUsername(String username);
}
