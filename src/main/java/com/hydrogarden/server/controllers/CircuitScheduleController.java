package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.services.CircuitScheduleService;
import com.hydrogarden.server.services.CircuitService;
import com.hydrogarden.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/circuit-schedule")
public class CircuitScheduleController {

    @Autowired
    private CircuitScheduleService circuitScheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CircuitService circuitService;



    @GetMapping("")
    public ResponseEntity<List<CircuitSchedule>> findAll() {
        return Optional.ofNullable(circuitScheduleService.findAll())
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @GetMapping("/{id}")
    public ResponseEntity<CircuitSchedule> findById(@PathVariable int id) {
        return circuitScheduleService.findScheduleById(id)
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PostMapping("/delete")
    public void deleteCircuitSchedule(@RequestBody CircuitSchedule circuitToDelete) {
        circuitScheduleService.deleteCircuitSchedule(circuitToDelete);
    }

    @PostMapping("/create")
    public ResponseEntity<CircuitSchedule> createCircuitSchedule(@NonNull @RequestBody CircuitSchedule circuitScheduleToCreate) {
        return Optional.of(circuitScheduleService.createCircuitSchedule(circuitScheduleToCreate))
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/update")
    public ResponseEntity<CircuitSchedule> updateCircuit(@NonNull @RequestBody CircuitSchedule circuitScheduleToUpdate) {
        //todo moze walidacja???
        return Optional.of(circuitScheduleService.updateCircuitSchedule(circuitScheduleToUpdate))
                .map(circuitSchedule -> new ResponseEntity<>(circuitSchedule, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Boolean> activate(@PathVariable int id){
        if(circuitScheduleService.activate(id)){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Boolean> deactivate(@PathVariable int id){
        if(circuitScheduleService.deactivate(id)){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);

    }
}
