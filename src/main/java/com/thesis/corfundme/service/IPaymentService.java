package com.thesis.corfundme.service;

import com.thesis.corfundme.model.User;
import com.thesis.corfundme.request.DonationPayRequest;

import java.util.List;

public interface IPaymentService {
  public List<String> getPaymentTypes();

  void pay(Integer donationId, User user, DonationPayRequest donationPayRequest);
}
