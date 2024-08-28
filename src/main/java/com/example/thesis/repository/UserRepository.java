package com.example.thesis.repository;

import com.example.thesis.model.User;
import com.example.thesis.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndPassword(String email, String password);

  boolean existsByRole(UserRole userRole);
}
