package com.hydrogarden.server.controllers;

import com.hydrogarden.server.domain.entities.Role;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.controllers.requestResponseEntities.UserLoginDTO;
import com.hydrogarden.server.controllers.requestResponseEntities.RegisterUserDTO;
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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://wiktor:3000"}, allowCredentials = "true")

@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {

        String encodedPassword = passwordEncoder.encode(registerUserDTO.getPassword());
        User user = User.builder().password(encodedPassword).username(registerUserDTO.getUsername()).role(Role.USER).build();
        try {
            User addedUser = userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        logger.info("/login Entry with parameters " + userLoginDTO);

        User user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtService.generateToken(new HashMap<>(), user);
            Cookie jwtCookie = new Cookie("Authorization", token);
            jwtCookie.setSecure(false);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(1000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            logger.info("/login Password accepted, sending OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("/login Password rejected, sending 400");
        return new ResponseEntity<>("Password or username is not correct", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Validated @NotNull @NotBlank @CookieValue("Authorization") Cookie jwtCookie, HttpServletRequest request, HttpServletResponse response) {
        logger.info("/logout Entry with parameters ");
        if(jwtCookie == null){
            logger.info("/logout entry without Authorization cookie, sending 400");
            return ResponseEntity.badRequest().build();
        }
        jwtCookie.setSecure(true);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        logger.info("/logout returing ok with logout cookie");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Valid @RequestBody() String token){
        logger.info("/verify entry with token: " + token);
        boolean valid = jwtService.isTokenValid(token);
        if(valid){
            logger.info("/verify token valid, sending OK");
            return ResponseEntity.ok().build();
        }
        logger.info("/verify token invalid, sending 404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
