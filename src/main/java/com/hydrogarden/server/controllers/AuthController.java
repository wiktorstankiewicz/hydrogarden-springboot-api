package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Role;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(!validUsername(registerDto.getUsername()) || ! validPassword(registerDto.getPassword())){
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder().password(registerDto.getPassword()).username(registerDto.getUsername()).role(Role.USER).build();

        User addedUser = userRepository.save(user);

        logger.info("Added user"+ addedUser.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean validUsername(String username) {
        logger.warning("Using stub method");
        return true;
    }

    private boolean validPassword(String password) {
        logger.warning("Using stub method");
        return true;
    }
}
