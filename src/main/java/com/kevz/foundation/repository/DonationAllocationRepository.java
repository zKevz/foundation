package com.kevz.foundation.repository;

import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationAllocationRepository extends JpaRepository<DonationAllocation, Integer> {
  List<DonationAllocation> findByDonationActivityAndDeletedFalse(DonationActivity donationActivity);
}
