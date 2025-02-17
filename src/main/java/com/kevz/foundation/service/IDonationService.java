package com.kevz.foundation.service;

import com.kevz.foundation.request.CreateDonationRequest;
import com.kevz.foundation.request.EditDonationRequest;
import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.response.DonationAllocationDetailResponse;
import com.kevz.foundation.response.DonationDetailResponse;
import com.kevz.foundation.response.DonationHeaderResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDonationService {
  List<DonationHeaderResponse> getNewestDonations();

  List<DonationHeaderResponse> getAll();

  void create(Integer foundationId, CreateDonationRequest createDonationRequest);
  Long getRemainingDonationDays(DonationActivity donationActivity);

  DonationActivity findById(Integer donationId);
  DonationDetailResponse getDetail(Integer donationId);

  DonationAllocationDetailResponse getAllocationDetail(Integer donationId);

  void editDonation(Integer donationId, EditDonationRequest editDonationRequest);

  void uploadImage(Integer donationId, MultipartFile file);
  void uploadImageProof(Integer donationId, MultipartFile file);

  void addDonationAllocation(Integer donationId, CreateDonationRequest.DonationAllocationItem addDonationAllocationRequest);

  void removeDonationAllocation(Integer donationId, Integer allocationId);
}
