package com.task.demo.services;

import com.task.demo.DTOs.request.LoginDTO;
import com.task.demo.DTOs.request.RegisterDTO;
import com.task.demo.DTOs.response.AuthResponseDTO;
import org.springframework.data.annotation.CreatedBy;

public interface AuthService {
    public void register(RegisterDTO registerDTO);
    public AuthResponseDTO login(LoginDTO loginDTO);
}
