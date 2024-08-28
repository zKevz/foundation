package com.thesis.corfundme.controller;

import com.thesis.corfundme.request.CreateDonationRequest;
import com.thesis.corfundme.request.EditDonationRequest;
import com.thesis.corfundme.response.BaseResponse;
import com.thesis.corfundme.response.DonationAllocationDetailResponse;
import com.thesis.corfundme.response.DonationDetailResponse;
import com.thesis.corfundme.response.DonationHeaderResponse;
import com.thesis.corfundme.service.IDonationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {
  @Autowired
  private IDonationService donationService;

  @GetMapping("/newest")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<List<DonationHeaderResponse>> getNewestDonations() {
    try {
      return BaseResponse.ok(donationService.getNewestDonations());
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<List<DonationHeaderResponse>> getAllDonations() {
    try {
      return BaseResponse.ok(donationService.getAll());
    } catch (Exception e) {
      log.error("Get all donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{foundationId}")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<Object> createDonation(@PathVariable Integer foundationId,
    @RequestBody CreateDonationRequest createDonationRequest) {
    try {
      donationService.create(foundationId, createDonationRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Create donation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationId}")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<DonationDetailResponse> getDetail(@PathVariable Integer donationId) {
    try {
      return BaseResponse.ok(donationService.getDetail(donationId));
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationId}/allocation")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<DonationAllocationDetailResponse> getAllocationDetail(@PathVariable Integer donationId) {
    try {
      return BaseResponse.ok(donationService.getAllocationDetail(donationId));
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PutMapping("/{donationId}")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<Object> editDonation(@PathVariable Integer donationId,
    @RequestBody EditDonationRequest editDonationRequest) {
    try {
      donationService.editDonation(donationId, editDonationRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Edit donation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
