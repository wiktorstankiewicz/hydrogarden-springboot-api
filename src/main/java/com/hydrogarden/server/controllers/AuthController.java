package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Role;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.controllers.requestResponseEntities.LoginRequestEntity;
import com.hydrogarden.server.controllers.requestResponseEntities.RegisterRequestEntity;
import com.hydrogarden.server.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestEntity registerRequestEntity) {
        String encodedPassword = passwordEncoder.encode(registerRequestEntity.getPassword());
        User user = User.builder().password(encodedPassword).username(registerRequestEntity.getUsername()).role(Role.USER).build();
        try {
            User addedUser = userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestEntity loginRequestEntity, HttpServletResponse response) {
        String username = loginRequestEntity.getUsername();
        String password = loginRequestEntity.getPassword();


        User user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtService.generateToken(new HashMap<>(), user);
            Cookie jwtCookie = new Cookie("Authorization", token);
            jwtCookie.setSecure(true);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(1000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Password or username is not correct", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Validated @NotNull @NotBlank @CookieValue("Authorization") String authCookie, HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("Authorization", "");
        jwtCookie.setSecure(true);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    public Map<String,String> handleMissingCookieExceptions(
            MissingRequestCookieException ex
    ){

        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getCookieName() + " cookie is missing");
        return errors;
    }

}
