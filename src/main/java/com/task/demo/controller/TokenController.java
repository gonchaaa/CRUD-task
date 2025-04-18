package com.task.demo.controller;

import com.task.demo.entities.User;
import com.task.demo.repository.UserRepository;
import com.task.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<?> getToken(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("İstifadəçi tapılmadı");
        }

        String token = jwtService.generateToken(user.orElse(null));
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
