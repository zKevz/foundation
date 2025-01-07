package com.thesis.corfundme.service;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.request.CreateDonationRequest;
import com.thesis.corfundme.request.EditDonationRequest;
import com.thesis.corfundme.response.DonationAllocationDetailResponse;
import com.thesis.corfundme.response.DonationDetailResponse;
import com.thesis.corfundme.response.DonationHeaderResponse;
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
