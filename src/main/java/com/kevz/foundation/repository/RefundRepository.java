package com.kevz.foundation.repository;

import com.kevz.foundation.model.DonationAllocated;
import com.kevz.foundation.model.User;
import com.kevz.foundation.model.Refund;
import com.kevz.foundation.model.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
  Optional<Refund> findByIdAndUser(Integer refundId, User user);

  Optional<Refund> findByDonationAllocated(DonationAllocated donationAllocated);

  List<Refund> findByStatusAndCreatedDateBefore(RefundStatus status, LocalDateTime localDateTime);
}
