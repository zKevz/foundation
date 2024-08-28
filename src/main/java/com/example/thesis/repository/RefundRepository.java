package com.example.thesis.repository;

import com.example.thesis.model.Refund;
import com.example.thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
  Optional<Refund> findByIdAndUser(Integer refundId, User user);
}
