package org.unibl.etf.ip.backendapp.service;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.backendapp.dto.LoginRequest;
import org.unibl.etf.ip.backendapp.dto.LoginResponse;
import org.unibl.etf.ip.backendapp.entity.User;
import org.unibl.etf.ip.backendapp.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse(user.getId(), user.getRole());
    }
}
