package com.hydrogarden.server.controllers;


import com.hydrogarden.server.controllers.requestResponseEntities.ConfirmExecutionOfTaskRequestBody;
import com.hydrogarden.server.controllers.requestResponseEntities.DeviceTaskResponseEntity;
import com.hydrogarden.server.domain.dto.GeneratedTaskDto;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.GeneratedTask;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.services.CircuitService;
import com.hydrogarden.server.services.GeneratedTaskService;
import com.hydrogarden.server.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.*;

@RestController
@RequestMapping("/hydroponic")
@RequiredArgsConstructor
public class HydroponicController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CircuitService circuitService;
    private final GeneratedTaskService generatedTaskService;
    private final UserService userService;

    @GetMapping("/get-task")
    public DeferredResult<ResponseEntity<DeviceTaskResponseEntity>> getTask() {
        DeferredResult<ResponseEntity<DeviceTaskResponseEntity>> result = new DeferredResult<>();
        DelayQueue<GeneratedTask> taskQueue =  generatedTaskService.getGeneratedTaskDelayQueue();
        try {
            GeneratedTask task = taskQueue.poll(30,TimeUnit.MINUTES);

            if(task == null){
                result.setErrorResult(ResponseEntity.notFound().build());
            }else{
                GeneratedTaskDto generatedTaskDto = new GeneratedTaskDto(task);
                result.setResult(ResponseEntity.ok().body(DeviceTaskResponseEntity.fromGeneratedTaskDto(generatedTaskDto)));
                taskQueue.put(task);
                //generatedTaskService.markGeneratedTaskDoneById(generatedTaskDto.getId());
            }

        } catch (InterruptedException e) {
            result.setErrorResult(ResponseEntity.internalServerError().build());
            throw new RuntimeException(e);
        }

        return result;
    }

    @PostMapping("/on")
    public ResponseEntity<String> on(@RequestBody int code){
        User user = userService.findByUsername("admin").get();
        Optional<Circuit> circuit = circuitService.findByCodeAndUser(code, user);
        if(circuit.isPresent()){
            generatedTaskService.addTask(new GeneratedTask(LocalDateTime.now(),true,false,user,circuit.get()));
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("Code not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/off")
    public ResponseEntity<String> off(@RequestBody int code){
        User user = userService.findByUsername("admin").get();
        Optional<Circuit> circuit = circuitService.findByCodeAndUser(code, user);
        if(circuit.isPresent()){
            generatedTaskService.addTask(new GeneratedTask(LocalDateTime.now(),false,false,user,circuit.get()));
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("Code not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/confirm-execution-of-task")
    public ResponseEntity<?> confirmExecutionOfTask(@RequestBody int generatedTaskId){
        boolean successful = generatedTaskService.markGeneratedTaskDoneById(generatedTaskId);
        successful &= generatedTaskService.getGeneratedTaskDelayQueue().removeIf(generatedTask -> generatedTask.getId() == generatedTaskId);
        if(successful){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
