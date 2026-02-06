package com.indichess.userservice.service;

import com.indichess.userservice.model.User;
import com.indichess.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUserByUsername(String username) {
        return userRepo.getUserByUsername(username);
    }

    public User findByEmail(String email){
        return userRepo.getUserByEmailId(email);
    }
    
    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
