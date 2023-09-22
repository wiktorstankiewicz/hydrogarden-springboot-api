package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.dto.UserDto;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.exceptions.UsernameTakenException;
import com.hydrogarden.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> get(@AuthenticationPrincipal User principal) {
        return userService.findById((int) principal.getId())
                .map(user -> new ResponseEntity<>(new UserDto(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PostMapping("/create")
    public ResponseEntity<User> addUser(@NonNull @RequestBody User userToCreate) {
        try{
            return Optional.of(userService.createUser(userToCreate))
                    .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }catch (UsernameTakenException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@NonNull @RequestBody User userToCreate) {
        return Optional.of(userService.updateUser(userToCreate))
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
