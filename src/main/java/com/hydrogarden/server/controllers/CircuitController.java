package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.services.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/circuit")
public class CircuitController {
    @Autowired
    private CircuitService circuitService;

    @GetMapping("")
    public List<Circuit> findAll(){
        return circuitService.findAll();
    }

    @GetMapping("/{id}")
    public Circuit findById(@PathVariable int id) {
    return circuitService.findById(id);}

}
