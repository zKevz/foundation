package com.example.thesis.service;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocation;

import java.util.List;

public interface IDonationAllocationService {
  Integer calculateAllocationAmountSum(DonationActivity donationActivity);

  DonationAllocation findById(Integer allocationId);
}
