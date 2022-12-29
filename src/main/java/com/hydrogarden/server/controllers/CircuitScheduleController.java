package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.services.CircuitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/circuit-schedule")
public class CircuitScheduleController {

    @Autowired
    private CircuitScheduleService circuitScheduleService;

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

    @GetMapping("/circuit/{circuitId}")
    public List<CircuitSchedule> findByCircuitId( @PathVariable int circuitId) {
        return circuitScheduleService.findByCircuit(circuitId);
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
}
