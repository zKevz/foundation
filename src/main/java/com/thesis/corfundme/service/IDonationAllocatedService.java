package com.thesis.corfundme.service;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.User;
import com.thesis.corfundme.response.DonationAllocatedDetailResponse;
import com.thesis.corfundme.response.DonationAllocatedHeaderResponse;

import java.util.List;

public interface IDonationAllocatedService {
  Integer calculateAllocatedAmountSum(DonationActivity donationActivity);

  List<DonationAllocatedHeaderResponse> getAllDonationAllocatedHeaders(User user);

  DonationAllocatedDetailResponse getDonationAllocatedDetail(Integer donationAllocatedId, User user);

  DonationAllocated findById(Integer donationAllocatedId);
}
