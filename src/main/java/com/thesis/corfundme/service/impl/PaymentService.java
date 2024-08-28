package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.DonationAllocatedStatus;
import com.thesis.corfundme.model.PaymentType;
import com.thesis.corfundme.model.User;
import com.thesis.corfundme.repository.DonationAllocatedRepository;
import com.thesis.corfundme.request.DonationPayRequest;
import com.thesis.corfundme.service.IDonationService;
import com.thesis.corfundme.service.IPaymentService;
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
