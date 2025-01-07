package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocation;
import com.thesis.corfundme.repository.DonationAllocationRepository;
import com.thesis.corfundme.service.IDonationAllocationService;
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
