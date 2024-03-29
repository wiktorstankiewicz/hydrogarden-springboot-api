package com.hydrogarden.server.controllers;

import com.hydrogarden.server.controllers.requestResponseEntities.RenameCircuitRequest;
import com.hydrogarden.server.domain.dto.CircuitDto;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.services.CircuitService;
import com.hydrogarden.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/circuit")
@CrossOrigin("*")
public class CircuitController {
    @Autowired
    private CircuitService circuitService;
    @Autowired
    private UserService userService;


    @PostMapping("/rename")
    public ResponseEntity<?> renameCircuit(@RequestBody RenameCircuitRequest body){
        Optional<Circuit> circuit = circuitService.findById(body.getId());
        if(circuit.isPresent()){
            circuit.get().setName(body.getCircuitName());
            circuitService.updateCircuit(circuit.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
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

    @GetMapping("")
    public ResponseEntity<List<CircuitDto>> find(@RequestParam(value = "circuitId", required = false) Integer circuitId) {
        User user = userService.findByUsername("admin").get();
        if (circuitId == null) {
            List<Circuit> circuits = circuitService.findByUserId((int) user.getId());
            return ResponseEntity.ok(circuits.stream().map(CircuitDto::new).toList());
        }

        Optional<Circuit> circuit = circuitService.findById(circuitId);
        return circuit.map(value -> ResponseEntity.ok(List.of(new CircuitDto(value)))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
