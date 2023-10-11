package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.dto.UpdateCircuitScheduleDTO;
import com.hydrogarden.server.domain.dto.CircuitScheduleDTO;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import com.hydrogarden.server.services.CircuitScheduleService;
import com.hydrogarden.server.services.CircuitService;
import com.hydrogarden.server.services.GeneratedTaskService;
import com.hydrogarden.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/circuit-schedule")
@CrossOrigin("*")
public class CircuitScheduleController {

    @Autowired
    private CircuitScheduleService circuitScheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CircuitService circuitService;

    @Autowired
    private GeneratedTaskService generatedTaskService;

    @GetMapping("")
    public ResponseEntity<List<CircuitScheduleDTO>> findAll() {
        return ResponseEntity.ok(circuitScheduleService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CircuitScheduleDTO> findById(@PathVariable int id) {
        return circuitScheduleService.findById(id)
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCircuitSchedule(@PathVariable("id") int id ) {
        boolean successful = circuitScheduleService.deleteById(id);
        if(successful){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<CircuitScheduleDTO> createCircuitSchedule(@NonNull @RequestBody CircuitScheduleDTO circuitScheduleToCreate) {
        return Optional.of(circuitScheduleService.create(circuitScheduleToCreate))
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/update")
    public ResponseEntity<CircuitScheduleDTO> updateCircuit(@NonNull @RequestBody UpdateCircuitScheduleDTO body) {
        return Optional.of(circuitScheduleService.updateCircuitSchedule(body.getId(), body.getStartDate(), body.getEndDate(), body.getStartTime(), body.getFrequencyDays(),body.getWateringTime()))
                .map(circuitScheduleDTO -> new ResponseEntity<>(circuitScheduleDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @PatchMapping("/activate/{id}")
    public ResponseEntity<Boolean> activate(@PathVariable int id) {
        if (circuitScheduleService.activate(id)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Boolean> deactivate(@PathVariable int id) {
        if (circuitScheduleService.deactivate(id)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    }
}
