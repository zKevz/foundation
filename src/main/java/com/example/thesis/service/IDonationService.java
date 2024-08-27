package com.example.thesis.service;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.request.CreateDonationRequest;
import com.example.thesis.response.DonationAllocationDetailResponse;
import com.example.thesis.response.DonationDetailResponse;
import com.example.thesis.response.DonationHeaderResponse;

import java.util.List;

public interface IDonationService {
  List<DonationHeaderResponse> getNewestDonations();

  List<DonationHeaderResponse> getAll();

  void create(Integer foundationId, CreateDonationRequest createDonationRequest);

  DonationActivity findById(Integer donationId);
  DonationDetailResponse getDetail(Integer donationId);

  DonationAllocationDetailResponse getAllocationDetail(Integer donationId);
}
