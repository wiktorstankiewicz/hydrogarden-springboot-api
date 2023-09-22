package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.dto.CircuitScheduleDto;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.repositories.CircuitScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public CircuitScheduleDto updateCircuitSchedule(CircuitScheduleDto circuitScheduleToUpdate) {
        CircuitSchedule circuitSchedule = circuitScheduleRepository.findById(circuitScheduleToUpdate.getId()).orElse(null);
        if (circuitSchedule == null) {
            return null;
        }
        circuitSchedule.setStartDate(circuitScheduleToUpdate.getStartDate());
        circuitSchedule.setEndDate(circuitScheduleToUpdate.getEndDate());
        circuitSchedule.setFrequencyDays(circuitScheduleToUpdate.getFrequencyDays());
        circuitSchedule.setWateringTime(circuitScheduleToUpdate.getWateringTime());
        return new CircuitScheduleDto(circuitSchedule);
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

    public CircuitSchedule updateCircuitSchedule(Integer id, LocalDate startDate, LocalDate endDate, LocalTime startTime, Integer frequencyDays, Integer wateringTime) {
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
            return cs;
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
