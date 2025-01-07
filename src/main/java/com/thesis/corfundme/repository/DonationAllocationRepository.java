package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationAllocationRepository extends JpaRepository<DonationAllocation, Integer> {
  List<DonationAllocation> findByDonationActivityAndDeletedFalse(DonationActivity donationActivity);
}
