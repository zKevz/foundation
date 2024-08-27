package com.example.thesis.service.impl;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocated;
import com.example.thesis.repository.DonationAllocatedRepository;
import com.example.thesis.service.IDonationAllocatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationAllocatedService implements IDonationAllocatedService {
  @Autowired
  private DonationAllocatedRepository donationAllocatedRepository;

  @Override
  public Integer calculateAllocatedAmountSum(DonationActivity donationActivity) {
    return donationAllocatedRepository.findByDonationActivity(donationActivity)
      .stream()
      .map(DonationAllocated::getAmount)
      .reduce(0, Integer::sum);
  }
}
