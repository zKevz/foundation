package com.example.thesis.service.impl;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocation;
import com.example.thesis.repository.DonationAllocationRepository;
import com.example.thesis.service.IDonationAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationAllocationService implements IDonationAllocationService {
  @Autowired
  private DonationAllocationRepository donationAllocationRepository;

  @Override
  public Integer calculateAllocationAmountSum(DonationActivity donationActivity) {
    return donationAllocationRepository
      .findByDonationActivity(donationActivity)
      .stream()
      .map(DonationAllocation::getAmount)
      .reduce(0, Integer::sum);
  }

  @Override
  public DonationAllocation findById(Integer allocationId) {
    return donationAllocationRepository.findById(allocationId)
      .orElseThrow(() -> new RuntimeException("Cannot find donation allocation with id " + allocationId));
  }
}
