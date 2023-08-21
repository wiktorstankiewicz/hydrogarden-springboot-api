package com.hydrogarden.server.controllers;

import com.fasterxml.jackson.annotation.JsonKey;
import com.hydrogarden.server.domain.entities.Role;
import com.hydrogarden.server.domain.entities.User;
import com.hydrogarden.server.domain.repositories.UserRepository;
import com.hydrogarden.server.dto.LoginDto;
import com.hydrogarden.server.dto.RegisterDto;
import com.hydrogarden.server.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = User.builder().password(encodedPassword).username(registerDto.getUsername()).role(Role.USER).build();
        try{
            User addedUser = userRepository.save(user);
        }catch(Exception e){
            return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();


        User user = userRepository.findByUsername(username);

        if(passwordEncoder.matches(password,user.getPassword())){
            String token = jwtService.generateToken(new HashMap<>(),user);
            Cookie jwtCookie = new Cookie("Authorization",token);
            jwtCookie.setSecure(true);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(1000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Password or username is not correct",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        if(request.getCookies() == null){
            return ResponseEntity.badRequest().body("Authorization cookie not found");
        }
        Cookie authCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("Authorization")).findFirst().orElse(null);
        if(authCookie != null){
            Cookie jwtCookie = new Cookie("Authorization","");
            jwtCookie.setSecure(true);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(0);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Authorization cookie not found");
    }
}
