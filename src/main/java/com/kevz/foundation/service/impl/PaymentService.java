package com.kevz.foundation.service.impl;

import com.kevz.foundation.request.DonationPayRequest;
import com.kevz.foundation.service.IDonationService;
import com.kevz.foundation.service.IPaymentService;
import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocated;
import com.kevz.foundation.model.PaymentType;
import com.kevz.foundation.model.User;
import com.kevz.foundation.repository.DonationAllocatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {

  @Autowired
  private IDonationService donationService;

  @Autowired
  private DonationAllocatedRepository donationAllocatedRepository;

  @Override
  public List<String> getPaymentTypes() {
    return Arrays.stream(PaymentType.values()).map(Enum::name).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void pay(Integer donationId, User user, DonationPayRequest donationPayRequest) {
    if (donationPayRequest.getAmount() <= 0) {
      throw new RuntimeException("Amount cannot be less than 1.");
    }

    DonationActivity donationActivity = donationService.findById(donationId);
    DonationAllocated donationAllocated = DonationAllocated
      .builder()
      .user(user)
      .donationActivity(donationActivity)
      .amount(donationPayRequest.getAmount())
      .paymentType(PaymentType.valueOf(donationPayRequest.getPaymentType()))
      .build();
    donationAllocatedRepository.save(donationAllocated);
  }
}
