package com.task.demo.services.impl;

import com.task.demo.DTOs.request.LoginDTO;
import com.task.demo.DTOs.request.RegisterDTO;
import com.task.demo.DTOs.response.AuthResponseDTO;
import com.task.demo.entities.User;
import com.task.demo.enums.Role;
import com.task.demo.repository.UserRepository;
import com.task.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO register(RegisterDTO registerDTO) {
        try {
            User user = new User();
            user.setFirstName(registerDTO.getFirstName());
            user.setLastName(registerDTO.getLastName());
            user.setAge(registerDTO.getAge());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(Role.USER);

            if (registerDTO.getBirthDate() != null) {
                user.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(registerDTO.getBirthDate()));
            }

            userRepository.save(user);
            String token = jwtService.generateToken(user);
            return new AuthResponseDTO(token);

        } catch (Exception e) {
            throw new RuntimeException("Qeydiyyat zamanı xəta baş verdi: " + e.getMessage());
        }
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }
}
