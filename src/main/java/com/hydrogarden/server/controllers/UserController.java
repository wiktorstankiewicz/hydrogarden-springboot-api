package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public User add(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody User user){
        try{
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(OptimisticLockingFailureException e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
