package com.hydrogarden.server.controllers;

import com.hydrogarden.server.controllers.requestResponseEntities.UpdateCircuitScheduleRequest;
import com.hydrogarden.server.domain.dto.CircuitScheduleDto;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<List<CircuitScheduleDto>> findAll() {
        return Optional.ofNullable(circuitScheduleService.findAll())
                .map(circuit -> new ResponseEntity<>(circuit.stream().map(CircuitScheduleDto::new).collect(Collectors.toList()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CircuitSchedule> findById(@PathVariable int id) {
        return circuitScheduleService.findScheduleById(id)
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCircuitSchedule(@PathVariable("id") int id ) {
        boolean successful =  circuitScheduleService.deleteById(id);
        if(successful){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<CircuitSchedule> createCircuitSchedule(@NonNull @RequestBody CircuitSchedule circuitScheduleToCreate) {
        return Optional.of(circuitScheduleService.createCircuitSchedule(circuitScheduleToCreate))
                .map(circuit -> new ResponseEntity<>(circuit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/update")
    public ResponseEntity<CircuitScheduleDto> updateCircuit(@NonNull @RequestBody UpdateCircuitScheduleRequest body) {
        return Optional.of(circuitScheduleService.updateCircuitSchedule(body.getId(), body.getStartDate(), body.getEndDate(), body.getStartTime(), body.getFrequencyDays(),body.getWateringTime()))
                .map(circuitSchedule -> new CircuitScheduleDto(circuitSchedule))
                .map(circuitScheduleDto -> new ResponseEntity<>(circuitScheduleDto, HttpStatus.OK))
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
