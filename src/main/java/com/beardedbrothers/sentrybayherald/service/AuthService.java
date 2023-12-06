package com.beardedbrothers.sentrybayherald.service;

import com.beardedbrothers.sentrybayherald.dto.LoginRequest;
import com.beardedbrothers.sentrybayherald.dto.RegisterRequest;
import com.beardedbrothers.sentrybayherald.model.User;
import com.beardedbrothers.sentrybayherald.repository.UserRepository;
import com.beardedbrothers.sentrybayherald.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        userRepository.save(user);
    }

    

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public LoginRequest login(LoginRequest loginRequest) {
        return loginRequest;
    }
}