package com.hydrogarden.server.circuit;

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
}
