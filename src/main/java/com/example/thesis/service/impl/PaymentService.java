package com.example.thesis.service.impl;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocated;
import com.example.thesis.model.DonationAllocatedStatus;
import com.example.thesis.model.PaymentType;
import com.example.thesis.model.User;
import com.example.thesis.repository.DonationAllocatedRepository;
import com.example.thesis.request.DonationPayRequest;
import com.example.thesis.service.IDonationService;
import com.example.thesis.service.IPaymentService;
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
    DonationActivity donationActivity = donationService.findById(donationId);
    DonationAllocated donationAllocated = DonationAllocated
      .builder()
      .user(user)
      .donationActivity(donationActivity)
      .amount(donationPayRequest.getAmount())
      .paymentType(PaymentType.valueOf(donationPayRequest.getPaymentType()))
      .status(DonationAllocatedStatus.SUCCESS)
      .build();
    donationAllocatedRepository.save(donationAllocated);
  }
}
