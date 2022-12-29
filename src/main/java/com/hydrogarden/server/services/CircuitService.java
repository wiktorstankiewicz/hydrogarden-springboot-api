package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.reporitiries.CircuitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CircuitService {
    @Autowired
    private CircuitRepository circuitRepository;
    public List<Circuit> findAll(){
        return circuitRepository.findAll();
    }

    public Circuit findById(int id) {
        return circuitRepository.findById(id).get();
    }
}
