package com.kevz.foundation.service.impl;

import com.kevz.foundation.service.IDonationAllocationService;
import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocation;
import com.kevz.foundation.repository.DonationAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationAllocationService implements IDonationAllocationService {
  @Autowired
  private DonationAllocationRepository donationAllocationRepository;

  @Override
  public Integer calculateAllocationAmountSum(DonationActivity donationActivity) {
    return donationAllocationRepository
      .findByDonationActivityAndDeletedFalse(donationActivity)
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
