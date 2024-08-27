package com.example.thesis.service;

import com.example.thesis.model.User;
import com.example.thesis.request.DonationPayRequest;

import java.util.List;

public interface IPaymentService {
  public List<String> getPaymentTypes();

  void pay(Integer donationId, User user, DonationPayRequest donationPayRequest);
}
