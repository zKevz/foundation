package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.User;
import com.thesis.corfundme.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmailAndDeletedFalse(String email);

  boolean existsByRole(UserRole userRole);
}
