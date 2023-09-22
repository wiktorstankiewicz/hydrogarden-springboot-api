package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.CircuitRepository;
import com.hydrogarden.server.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CircuitService {
    @Autowired
    private CircuitRepository circuitRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Circuit> findAll() {
        return circuitRepository.findAll();
    }

    public Optional<Circuit> findById(int id) {
        return circuitRepository.findById(id);
    }

    public List<Circuit> findByUserId(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? circuitRepository.findByUser(user) : Collections.emptyList();
    }

    public void deleteCircuit(Circuit circuitToDelete) {
        circuitRepository.delete(circuitToDelete);
    }

    public Circuit createCircuit(Circuit circutToCreate) {
        return circuitRepository.save(circutToCreate);
    }

    public Circuit updateCircuit(Circuit circuitToUpdate) {
        //todo a moze podobna walidacja jak w userservice???
        return circuitRepository.save(circuitToUpdate);
    }

    public Optional<Circuit> findByCodeAndUser(int circuitCode, User user) {
        return circuitRepository.findCircuitByCodeAndUser(circuitCode,user);
    }
}
