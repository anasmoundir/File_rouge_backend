package com.example.demo.service.interfaces;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.JwtResponseDTO;

public interface  LoginService {
    JwtResponseDTO authenticateUser(AuthRequestDTO authRequestDTO);

    boolean isEnable(String username);

    String getUserRole(String username);

}
