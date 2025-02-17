package com.kevz.foundation.controller;

import com.kevz.foundation.request.DonationPayRequest;
import com.kevz.foundation.response.BaseResponse;
import com.kevz.foundation.service.IPaymentService;
import com.kevz.foundation.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
  @Autowired
  private IPaymentService paymentService;

  @GetMapping
  public BaseResponse<List<String>> getPaymentTypes() {
    return BaseResponse.ok(paymentService.getPaymentTypes());
  }

  @PostMapping("/{donationId}/_pay")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<Object> pay(@AuthenticationPrincipal User user,
    @PathVariable Integer donationId,
    @RequestBody DonationPayRequest donationPayRequest) {
    try {
      paymentService.pay(donationId, user, donationPayRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Pay donation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
