package com.kevz.foundation.repository;

import com.kevz.foundation.model.User;
import com.kevz.foundation.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmailAndDeletedFalse(String email);

  boolean existsByRole(UserRole userRole);

  boolean existsByEmail(String email);
}
