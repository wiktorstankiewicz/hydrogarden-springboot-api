package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.repositories.CircuitScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CircuitScheduleService {
    @Autowired
    private CircuitScheduleRepository circuitScheduleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CircuitService circuitService;

    public Optional<CircuitSchedule> findScheduleById(int id) {
        return circuitScheduleRepository.findById(id);
    }

    public void removeAllSchedules() {
        circuitScheduleRepository.deleteAll();
    }

    public List<CircuitSchedule> findAll() {
        return circuitScheduleRepository.findAll();
    }

    public CircuitSchedule createCircuitSchedule(CircuitSchedule circuitScheduleToCreate) {
        return circuitScheduleRepository.save(circuitScheduleToCreate);
    }

    public void deleteCircuitSchedule(CircuitSchedule circuitToDelete) {
        circuitScheduleRepository.delete(circuitToDelete);
    }

    public CircuitSchedule updateCircuitSchedule(CircuitSchedule circuitScheduleToUpdate) {
        return circuitScheduleRepository.save(circuitScheduleToUpdate);
    }



    public boolean activate(int id) {
        Optional<CircuitSchedule> optionalCircuitSchedule = circuitScheduleRepository.findById(id);
        if(optionalCircuitSchedule.isEmpty()) return false;
        CircuitSchedule circuitSchedule = optionalCircuitSchedule.get();
        circuitSchedule.setDeactivated(false);
        circuitScheduleRepository.save(circuitSchedule);
        return true;
    }

    public boolean deactivate(int id) {
        Optional<CircuitSchedule> optionalCircuitSchedule = circuitScheduleRepository.findById(id);
        if(optionalCircuitSchedule.isEmpty()) return false;
        CircuitSchedule circuitSchedule = optionalCircuitSchedule.get();
        circuitSchedule.setDeactivated(true);
        circuitScheduleRepository.save(circuitSchedule);
        return true;
    }
}
