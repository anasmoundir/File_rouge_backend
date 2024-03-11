package com.example.demo.dto;

import com.example.demo.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private UserRole userRole;
}
