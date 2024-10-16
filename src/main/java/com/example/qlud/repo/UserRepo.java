package com.example.qlud.repo;

import com.example.qlud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByUserEmail(String userEmail);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    boolean existsByUserEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
