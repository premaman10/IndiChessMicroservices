package com.indichess.authservice.service;

import com.indichess.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        // In a real microservice, you might want to call user-service to check existence
        // OR let the user-service handle the unique constraint error.
        // For now, we'll follow the original logic and call the user-service via Feign.
        
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Default rating
        user.setRating(250);

        return userClient.saveUser(user);
    }
}
