package com.kevz.foundation.service;

import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocated;
import com.kevz.foundation.model.User;
import com.kevz.foundation.response.DonationAllocatedDetailResponse;
import com.kevz.foundation.response.DonationAllocatedHeaderResponse;

import java.util.List;

public interface IDonationAllocatedService {
  Integer calculateAllocatedAmountSum(DonationActivity donationActivity);

  List<DonationAllocatedHeaderResponse> getAllDonationAllocatedHeaders(User user);

  DonationAllocatedDetailResponse getDonationAllocatedDetail(Integer donationAllocatedId, User user);

  DonationAllocated findById(Integer donationAllocatedId);
}
