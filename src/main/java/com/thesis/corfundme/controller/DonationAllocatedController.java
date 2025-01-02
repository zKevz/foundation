package com.thesis.corfundme.controller;

import com.thesis.corfundme.model.User;
import com.thesis.corfundme.response.BaseResponse;
import com.thesis.corfundme.response.DonationAllocatedDetailResponse;
import com.thesis.corfundme.response.DonationAllocatedHeaderResponse;
import com.thesis.corfundme.service.IDonationAllocatedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/donations/allocated")
public class DonationAllocatedController {
  @Autowired
  private IDonationAllocatedService donationAllocatedService;

  @GetMapping
  @PreAuthorize("hasRole('USER') or hasRole('FOUNDATION')")
  public BaseResponse<List<DonationAllocatedHeaderResponse>> getAllDonationAllocatedHeaders(
    @AuthenticationPrincipal User user) {
    try {
      return BaseResponse.ok(donationAllocatedService.getAllDonationAllocatedHeaders(user));
    } catch (Exception e) {
      log.error("Get all donation allocated headers error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationAllocatedId}")
  @PreAuthorize("hasRole('USER') or hasRole('FOUNDATION')")
  public BaseResponse<DonationAllocatedDetailResponse> getDonationAllocatedDetail(@AuthenticationPrincipal User user,
    @PathVariable Integer donationAllocatedId) {
    try {
      return BaseResponse.ok(donationAllocatedService.getDonationAllocatedDetail(donationAllocatedId, user));
    } catch (Exception e) {
      log.error("Get all donation allocated detail error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
