package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.services.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/circuit")
public class CircuitController {
    @Autowired
    private CircuitService circuitService;

    @GetMapping("")
    public ResponseEntity<List<Circuit>> findAll() {
        return Optional.ofNullable(circuitService.findAll())
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Circuit> findById(@PathVariable int id) {
        return circuitService.findById(id)
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{id}")
    public List<Circuit> findByUserId(@PathVariable int id) {
        return circuitService.findByUserId(id);
    }

    @PostMapping("/delete")
    public void deleteCircuit(@RequestBody Circuit circuitToDelete) {
        circuitService.deleteCircuit(circuitToDelete);
    }

    @PostMapping("/create")
    public ResponseEntity<Circuit> createCircuit(@NonNull @RequestBody Circuit circuitToCreate) {
        return Optional.of(circuitService.createCircuit(circuitToCreate))
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/update")
    public ResponseEntity<Circuit> updateCircuit(@NonNull @RequestBody Circuit circuitToUpdate) {
        return Optional.of(circuitService.updateCircuit(circuitToUpdate))
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
