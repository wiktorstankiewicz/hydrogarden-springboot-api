package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.UserDTO;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.mappers.CircuitMapper;
import com.hydrogarden.server.domain.mappers.CircuitScheduleMapper;
import com.hydrogarden.server.domain.mappers.UserMapper;
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
                map(circuitSchedule -> CircuitScheduleMapper.fromEntity(circuitSchedule));
    }

    public void deleteAll() {
        circuitScheduleRepository.deleteAll();
    }

    public List<CircuitScheduleDTO> findAll() {
        return circuitScheduleRepository.findAll().stream()
                .map(circuitSchedule -> CircuitScheduleMapper.fromEntity(circuitSchedule))
                .collect(Collectors.toList());
    }

    public CircuitScheduleDTO create(CircuitScheduleDTO dto) {
        return CircuitScheduleMapper.fromEntity(circuitScheduleRepository.save(CircuitScheduleMapper.fromDTO(dto)));
    }

    public void delete(CircuitScheduleDTO circuitToDelete) {
        circuitScheduleRepository.delete(CircuitScheduleMapper.fromDTO(circuitToDelete));
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
            return CircuitScheduleMapper.fromEntity(cs);
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
