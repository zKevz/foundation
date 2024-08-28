package com.thesis.corfundme.service;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocation;

public interface IDonationAllocationService {
  Integer calculateAllocationAmountSum(DonationActivity donationActivity);

  DonationAllocation findById(Integer allocationId);
}
