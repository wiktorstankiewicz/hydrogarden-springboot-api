package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.repositories.CircuitScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CircuitScheduleService {
    @Autowired
    private CircuitScheduleRepository circuitScheduleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CircuitService circuitService;

    public Optional<CircuitScheduleDTO> findById(int id) {
        return circuitScheduleRepository.findById(id).
                map(circuitSchedule -> new CircuitScheduleDTO(circuitSchedule,new UserDTO(circuitSchedule.getCircuit().getUser())));
    }

    public void deleteAll() {
        circuitScheduleRepository.deleteAll();
    }

    public List<CircuitScheduleDTO> findAll() {
        return circuitScheduleRepository.findAll().stream().map(circuitSchedule -> new CircuitScheduleDTO(circuitSchedule,new UserDTO(circuitSchedule.getCircuit().getUser())) ).collect(Collectors.toList());
    }

    public CircuitScheduleDTO create(CircuitScheduleDTO dto) {
        return new CircuitScheduleDTO(circuitScheduleRepository.save(CircuitSchedule.fromCircuitScheduleDTO(dto)),dto.getCircuitDto().getUser());
    }

    public void delete(CircuitScheduleDTO circuitToDelete) {
        circuitScheduleRepository.delete(CircuitSchedule.fromCircuitScheduleDTO(circuitToDelete));
    }




    public boolean activate(int id) {
        Optional<CircuitSchedule> optionalCircuitSchedule = circuitScheduleRepository.findById(id);
        if (optionalCircuitSchedule.isEmpty()) return false;
        CircuitSchedule circuitSchedule = optionalCircuitSchedule.get();
        circuitSchedule.setDeactivated(false);
        circuitScheduleRepository.save(circuitSchedule);
        return true;
    }

    public boolean deactivate(int id) {
        Optional<CircuitSchedule> optionalCircuitSchedule = circuitScheduleRepository.findById(id);
        if (optionalCircuitSchedule.isEmpty()) return false;
        CircuitSchedule circuitSchedule = optionalCircuitSchedule.get();
        circuitSchedule.setDeactivated(true);
        circuitScheduleRepository.save(circuitSchedule);
        return true;
    }

    public CircuitScheduleDTO updateCircuitSchedule(Integer id, LocalDate startDate, LocalDate endDate, LocalTime startTime, Integer frequencyDays, Integer wateringTime) {
        if (id == null) {
            return null;
        }
        Optional<CircuitSchedule> circuitSchedule = circuitScheduleRepository.findById(id);
        if (circuitSchedule.isPresent()) {
            CircuitSchedule cs = circuitSchedule.get();

            if (startTime != null) {
                cs.setStartTime(startTime);
            }
            if (startDate != null) {
                cs.setStartDate(startDate);
            }
            if (endDate != null) {
                cs.setEndDate(endDate);
            }
            if (frequencyDays != null) {
                cs.setFrequencyDays(frequencyDays);
            }
            if (wateringTime != null) {
                cs.setWateringTime(wateringTime);
            }
            circuitScheduleRepository.save(cs);
            return new CircuitScheduleDTO(cs,new UserDTO(cs.getCircuit().getUser()));
        }
        return null;
    }

    public boolean deleteById(int id) {
        try {
            circuitScheduleRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            return false;

        }
    }
}
