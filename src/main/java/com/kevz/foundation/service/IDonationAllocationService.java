package com.kevz.foundation.service;

import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocation;

public interface IDonationAllocationService {
  Integer calculateAllocationAmountSum(DonationActivity donationActivity);

  DonationAllocation findById(Integer allocationId);
}
