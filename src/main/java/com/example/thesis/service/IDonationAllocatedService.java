package com.example.thesis.service;

import com.example.thesis.model.DonationActivity;

import java.util.List;

public interface IDonationAllocatedService {
  Integer calculateAllocatedAmountSum(DonationActivity donationActivity);
}
