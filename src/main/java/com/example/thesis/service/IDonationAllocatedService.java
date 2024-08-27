package com.example.thesis.service;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.User;
import com.example.thesis.response.DonationAllocatedDetailResponse;
import com.example.thesis.response.DonationAllocatedHeaderResponse;

import java.util.List;

public interface IDonationAllocatedService {
  Integer calculateAllocatedAmountSum(DonationActivity donationActivity);

  List<DonationAllocatedHeaderResponse> getAllDonationAllocatedHeaders(User user);

  DonationAllocatedDetailResponse getDonationAllocatedDetail(Integer donationAllocatedId, User user);
}
