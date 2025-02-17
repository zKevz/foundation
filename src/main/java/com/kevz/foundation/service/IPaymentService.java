package com.kevz.foundation.service;

import com.kevz.foundation.request.DonationPayRequest;
import com.kevz.foundation.model.User;

import java.util.List;

public interface IPaymentService {
  List<String> getPaymentTypes();

  void pay(Integer donationId, User user, DonationPayRequest donationPayRequest);
}
