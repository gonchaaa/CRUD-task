package com.task.demo.services;

import com.task.demo.DTOs.request.LoginDTO;
import com.task.demo.DTOs.request.RegisterDTO;
import com.task.demo.DTOs.response.AuthResponseDTO;

public interface AuthService {
    public AuthResponseDTO register(RegisterDTO registerDTO);
    public AuthResponseDTO login(LoginDTO loginDTO);
}
