package com.hydrogarden.server.services;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import com.hydrogarden.server.domain.repositories.CircuitScheduleRepository;
import com.hydrogarden.server.domain.repositories.GeneratedTaskRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.DelayQueue;

@Service
@DependsOnDatabaseInitialization
public class GeneratedTaskService {

    private final GeneratedTaskRepository generatedTaskRepository;
    private final CircuitScheduleRepository circuitScheduleRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DelayQueue<GeneratedTask> generatedTaskDelayQueue = new DelayQueue<GeneratedTask>();
    public GeneratedTaskService(GeneratedTaskRepository generatedTaskRepository, CircuitScheduleRepository circuitScheduleRepository) {
        this.generatedTaskRepository = generatedTaskRepository;
        this.circuitScheduleRepository = circuitScheduleRepository;
        //delete all undone generated tasks
        generatedTaskRepository.deleteByDone(false);
        List<GeneratedTask> generatedTasks = generateTasksToPerform();
        generatedTaskRepository.saveAll(generatedTasks);
        generatedTaskDelayQueue.addAll(generatedTasks);
        logger.debug("GeneratedTaskService was instantiated");
    }

    public DelayQueue<GeneratedTask> getGeneratedTaskDelayQueue(){
        return generatedTaskDelayQueue;
    }
    @Transactional
    private List<GeneratedTask> generateTasksToPerform() {
        List<CircuitSchedule> circuitScheduleList = circuitScheduleRepository.findAllAndFetchAllProperties();
        List<GeneratedTask> generatedTaskList = new ArrayList<>();

        for(CircuitSchedule circuitSchedule: circuitScheduleList){
            List<GeneratedTask> generatedTasksFromSchedule = generateTasksFromSchedule(circuitSchedule);
            generatedTaskList.addAll(generatedTasksFromSchedule);
        }
        return generatedTaskList;
    }

    @Transactional
    private List<GeneratedTask> generateTasksFromSchedule(CircuitSchedule circuitSchedule) {
        LocalTime startTime = circuitSchedule.getStartTime();
        LocalDate startDate = circuitSchedule.getStartDate();
        LocalDate endDate = circuitSchedule.getEndDate();

        List<GeneratedTask> generatedTaskList = new ArrayList<>();


        int frequencyDays = circuitSchedule.getFrequencyDays();
        LocalDateTime currentStartDateTime = startDate.atTime(startTime);
        while(currentStartDateTime.isBefore(LocalDateTime.now())) {
            currentStartDateTime = currentStartDateTime.plusDays(frequencyDays);
        }
        while(currentStartDateTime.isBefore(endDate.atTime(LocalTime.MAX))){
            GeneratedTask generatedStartTask = new GeneratedTask(currentStartDateTime,true,false,circuitSchedule.getCircuit().getUser(), circuitSchedule.getCircuit(),circuitSchedule);
            GeneratedTask generatedEndTask = new GeneratedTask(currentStartDateTime.plusSeconds(circuitSchedule.getWateringTime()),false,false,circuitSchedule.getCircuit().getUser(), circuitSchedule.getCircuit(),circuitSchedule);
            generatedTaskList.add(generatedStartTask);
            generatedTaskList.add(generatedEndTask);
            currentStartDateTime = currentStartDateTime.plusDays(frequencyDays);
        }


        return generatedTaskList;
    }

    private void markOutdatedTasks() {

    }


    public boolean markGeneratedTaskDoneById(long id) {
        GeneratedTask generatedTask = generatedTaskRepository.findById((int) id).orElse(null);
        if(generatedTask != null){
            generatedTask.setDone(true);
            generatedTaskRepository.save(generatedTask);
            return true;
        }
        return false;
    }

    public void addTask(GeneratedTask generatedTask) {
        generatedTaskRepository.save(generatedTask);
        if(!generatedTask.isDone()){
            generatedTaskDelayQueue.add(generatedTask);
        }
    }

    public Optional<GeneratedTask> findById(int generatedTaskId) {
        return generatedTaskRepository.findById(generatedTaskId);
    }
}
