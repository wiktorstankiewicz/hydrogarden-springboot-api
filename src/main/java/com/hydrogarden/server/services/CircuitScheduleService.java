package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.reporitiries.CircuitSchedule;
import com.hydrogarden.server.domain.reporitiries.CircuitScheduleRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CircuitScheduleService{
    @Autowired
    private CircuitScheduleRepository scheduleRepository;

    public CircuitSchedule addSchedule(CircuitSchedule circuitSchedule){
        return scheduleRepository.save(circuitSchedule);
    }
    @Nullable
    public CircuitSchedule findScheduleById(int id){
        return scheduleRepository.findById(id).orElse(null);
    }

    public void removeScheduleById(int id){
        scheduleRepository.deleteById(id);
    }

    public void removeAllSchedules(){
        scheduleRepository.deleteAll();
    }
}
