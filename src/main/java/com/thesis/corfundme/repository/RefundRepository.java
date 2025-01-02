package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.Refund;
import com.thesis.corfundme.model.RefundStatus;
import com.thesis.corfundme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
  Optional<Refund> findByIdAndUser(Integer refundId, User user);

  Optional<Refund> findByDonationAllocated(DonationAllocated donationAllocated);

  List<Refund> findByStatusAndCreatedDateBefore(RefundStatus status, LocalDateTime localDateTime);
}
