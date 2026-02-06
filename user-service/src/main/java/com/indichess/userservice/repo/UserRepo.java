package com.indichess.userservice.repo;

import com.indichess.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User getUserByUsername(String username);
    User getUserByEmailId(String email);
    boolean existsByEmailId(String emailId);
    boolean existsByUsername(String username);
}
