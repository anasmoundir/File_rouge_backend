package com.example.demo.service.serviceImplementation;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.config.ConfigService.JwtService;
import com.example.demo.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public JwtResponseDTO authenticateUser(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );

            if (!isEnable(authRequestDTO.getUsername())) {
                String activationMessage = "User account is not activated. Please check your email for activation instructions.";
                throw new AuthenticationException(activationMessage) {};
            }

            if (authentication.isAuthenticated()) {
                logUserInfo(authentication);

                User user = userRepository.findByUsername(authRequestDTO.getUsername());
                String role = getUserRole(authRequestDTO.getUsername());
                String generatedToken = jwtService.generateToken(authRequestDTO.getUsername(), role);
                String refreshToken = jwtService.generateRefreshToken(authRequestDTO.getUsername(), role);

                return JwtResponseDTO.builder().accessToken(generatedToken).refreshToken(refreshToken).build();
            } else {
                String errorMessage = "Invalid user credentials.";
                throw new AuthenticationException(errorMessage) {};
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid user credentials.") {};
        }
    }



    private void logUserInfo(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("User logged in: " + username);


        System.out.println("User authorities (roles):");
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println("- " + authority.getAuthority());
        }

    }


    @Override
    public boolean isEnable(String username) {
        User user = userRepository.findByUsername(username);
        return user.isEnabled();
    }

    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getUserRole() != null ? user.getUserRole().getRoleName() : null;
    }
}
