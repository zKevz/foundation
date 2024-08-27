package com.example.thesis.repository;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationAllocationRepository extends JpaRepository<DonationAllocation, Integer> {
  List<DonationAllocation> findByDonationActivity(DonationActivity donationActivity);
}
