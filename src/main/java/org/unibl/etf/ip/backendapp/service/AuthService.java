package org.unibl.etf.ip.backendapp.service;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.backendapp.dto.LoginRequest;
import org.unibl.etf.ip.backendapp.dto.LoginResponse;
import org.unibl.etf.ip.backendapp.entity.User;
import org.unibl.etf.ip.backendapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

   public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
   }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return new LoginResponse(user.getId(), user.getRole());
    }
}
