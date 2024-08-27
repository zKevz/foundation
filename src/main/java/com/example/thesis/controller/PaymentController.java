package com.example.thesis.controller;

import com.example.thesis.model.User;
import com.example.thesis.request.DonationPayRequest;
import com.example.thesis.response.BaseResponse;
import com.example.thesis.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public BaseResponse<Object> pay(@AuthenticationPrincipal User user,
    @PathVariable Integer donationId,
    @RequestBody DonationPayRequest donationPayRequest) {
    try {
      paymentService.pay(donationId, user, donationPayRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      return BaseResponse.error(e.getMessage());
    }
  }
}
