package com.example.thesis.service.impl;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocated;
import com.example.thesis.model.User;
import com.example.thesis.repository.DonationAllocatedRepository;
import com.example.thesis.response.DonationAllocatedDetailResponse;
import com.example.thesis.response.DonationAllocatedHeaderResponse;
import com.example.thesis.service.IDonationAllocatedService;
import com.example.thesis.service.IDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationAllocatedService implements IDonationAllocatedService {
  @Autowired
  private DonationAllocatedRepository donationAllocatedRepository;

  @Autowired
  @Lazy
  private IDonationService donationService;

  @Override
  public Integer calculateAllocatedAmountSum(DonationActivity donationActivity) {
    return donationAllocatedRepository
      .findByDonationActivity(donationActivity)
      .stream()
      .map(DonationAllocated::getAmount)
      .reduce(0, Integer::sum);
  }

  private DonationAllocatedHeaderResponse mapDonationAllocatedToHeaderResponse(DonationAllocated donationAllocated) {
    return DonationAllocatedHeaderResponse
      .builder()
      .id(donationAllocated.getId())
      .name(donationAllocated.getDonationActivity().getName())
      .imageUrl(donationAllocated.getDonationActivity().getImageUrl())
      .foundationName(donationAllocated.getDonationActivity().getFoundation().getUser().getName())
      .amount(donationAllocated.getAmount())
      .date(donationAllocated.getCreatedDate())
      .build();
  }

  @Override
  public List<DonationAllocatedHeaderResponse> getAllDonationAllocatedHeaders(User user) {
    List<DonationAllocated> donationAllocatedList = donationAllocatedRepository.findByUser(user);
    return donationAllocatedList.stream().map(this::mapDonationAllocatedToHeaderResponse).collect(Collectors.toList());
  }

  @Override
  public DonationAllocatedDetailResponse getDonationAllocatedDetail(Integer donationAllocatedId, User user) {
    DonationAllocated donationAllocated = donationAllocatedRepository.findByUserAndId(user, donationAllocatedId);
    return DonationAllocatedDetailResponse
      .builder()
      .date(donationAllocated.getCreatedDate())
      .id(donationAllocated.getId())
      .amount(donationAllocated.getAmount())
      .remainingDays(donationService.getRemainingDonationDays(donationAllocated.getDonationActivity()))
      .name(donationAllocated.getDonationActivity().getName())
      .foundationName(donationAllocated.getDonationActivity().getFoundation().getUser().getName())
      .proofImageUrl(donationAllocated.getDonationActivity().getImageProofUrl())
      .donationStatus(donationAllocated.getStatus())
      .donationShipmentStatus(donationAllocated.getDonationActivity().getShipmentStatus())
      .build();
  }
}
