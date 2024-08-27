package com.example.thesis.controller;

import com.example.thesis.model.User;
import com.example.thesis.response.BaseResponse;
import com.example.thesis.response.DonationAllocatedDetailResponse;
import com.example.thesis.response.DonationAllocatedHeaderResponse;
import com.example.thesis.service.IDonationAllocatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donation-allocated")
public class DonationAllocatedController {
  @Autowired
  private IDonationAllocatedService donationAllocatedService;

  @GetMapping
  private BaseResponse<List<DonationAllocatedHeaderResponse>> getAllDonationAllocatedHeaders(
    @AuthenticationPrincipal User user) {
    try {
      return BaseResponse.ok(donationAllocatedService.getAllDonationAllocatedHeaders(user));
    } catch (Exception e) {
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationAllocatedId}")
  private BaseResponse<DonationAllocatedDetailResponse> getDonationAllocatedDetail(@AuthenticationPrincipal User user,
    @PathVariable Integer donationAllocatedId) {
    try {
      return BaseResponse.ok(donationAllocatedService.getDonationAllocatedDetail(donationAllocatedId, user));
    } catch (Exception e) {
      return BaseResponse.error(e.getMessage());
    }
  }
}
