package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.dto.CircuitDTO;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.mappers.CircuitMapper;
import com.hydrogarden.server.domain.repositories.CircuitRepository;
import com.hydrogarden.server.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CircuitService {
    @Autowired
    private CircuitRepository circuitRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<CircuitDTO> findById(int id) {
        return circuitRepository.findById(id)
                .map(circuit -> CircuitMapper.fromEntity(circuit));
    }

    public List<CircuitDTO> findByUserId(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? circuitRepository.findByUser(user).stream()
                .map(circuit -> CircuitMapper.fromEntity(circuit))
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public void deleteCircuit(Circuit circuitToDelete) {
        circuitRepository.delete(circuitToDelete);
    }

    public Circuit createCircuit(Circuit circutToCreate) {
        return circuitRepository.save(circutToCreate);
    }

    public CircuitDTO updateCircuit(CircuitDTO circuitToUpdate) {
        //todo a moze podobna walidacja jak w userservice???
        return CircuitMapper.fromEntity(circuitRepository.save(CircuitMapper.fromDTO(circuitToUpdate)));
    }

    public Optional<Circuit> findByCodeAndUser(int circuitCode, User user) {
        return circuitRepository.findCircuitByCodeAndUser(circuitCode,user);
    }
}
