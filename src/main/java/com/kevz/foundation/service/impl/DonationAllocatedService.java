package com.kevz.foundation.service.impl;

import com.kevz.foundation.service.IDonationAllocatedService;
import com.kevz.foundation.service.IDonationService;
import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocated;
import com.kevz.foundation.model.Refund;
import com.kevz.foundation.model.RefundStatus;
import com.kevz.foundation.model.User;
import com.kevz.foundation.repository.DonationAllocatedRepository;
import com.kevz.foundation.repository.RefundRepository;
import com.kevz.foundation.response.DonationAllocatedDetailResponse;
import com.kevz.foundation.response.DonationAllocatedHeaderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationAllocatedService implements IDonationAllocatedService {
  @Autowired
  private DonationAllocatedRepository donationAllocatedRepository;

  @Autowired
  @Lazy
  private IDonationService donationService;

  @Autowired
  private RefundRepository refundRepository;

  @Override
  public Integer calculateAllocatedAmountSum(DonationActivity donationActivity) {
    return donationAllocatedRepository
      .findByDonationActivityAndDeletedFalse(donationActivity)
      .stream()
      .map(DonationAllocated::getAmount)
      .reduce(0, Integer::sum);
  }

  private DonationAllocatedHeaderResponse mapDonationAllocatedToHeaderResponse(DonationAllocated donationAllocated) {
    Optional<Refund> refund = refundRepository.findByDonationAllocated(donationAllocated);
    return DonationAllocatedHeaderResponse
      .builder()
      .id(donationAllocated.getId())
      .name(donationAllocated.getDonationActivity().getName())
      .status(refund.map(Refund::getStatus).map(RefundStatus::getValue).orElse("Berhasil"))
      .imageUrl(donationAllocated.getDonationActivity().getImageUrl())
      .foundationName(donationAllocated.getDonationActivity().getFoundation().getUser().getName())
      .amount(donationAllocated.getAmount())
      .date(donationAllocated.getCreatedDate())
      .build();
  }

  @Override
  public List<DonationAllocatedHeaderResponse> getAllDonationAllocatedHeaders(User user) {
    List<DonationAllocated> donationAllocatedList = donationAllocatedRepository.findByUserAndDeletedFalse(user);
    return donationAllocatedList.stream().map(this::mapDonationAllocatedToHeaderResponse).collect(Collectors.toList());
  }

  @Override
  public DonationAllocatedDetailResponse getDonationAllocatedDetail(Integer donationAllocatedId, User user) {
    DonationAllocated donationAllocated = donationAllocatedRepository
      .findByUserAndIdAndDeletedFalse(user, donationAllocatedId)
      .orElseThrow(() -> new RuntimeException(
        "Cannot find donation allocated by id " + donationAllocatedId + " and user id " + user.getId()));

    Optional<Refund> refund = refundRepository.findByDonationAllocated(donationAllocated);
    Date shipmentDate = Optional
      .ofNullable(donationAllocated.getDonationActivity().getArrivedDate())
      .orElse(donationAllocated.getDonationActivity().getDeliveredDate());

    return DonationAllocatedDetailResponse
      .builder()
      .id(donationAllocated.getId())
      .amount(donationAllocated.getAmount())
      .remainingDays(donationService.getRemainingDonationDays(donationAllocated.getDonationActivity()))
      .name(donationAllocated.getDonationActivity().getName())
      .refundReason(refund.map(Refund::getReason).orElse(null))
      .proofImageUrl(donationAllocated.getDonationActivity().getImageProofUrl())
      .foundationName(donationAllocated.getDonationActivity().getFoundation().getUser().getName())
      .refundRejectReason(refund.map(Refund::getRejectReason).orElse(null))
      .refundStatus(refund.map(Refund::getStatus).map(RefundStatus::getValue).orElse(null))
      .activityStatus(donationAllocated.getDonationActivity().getStatus().getValue())
      .shipmentStatus(donationAllocated.getDonationActivity().getShipmentStatus().getValue())
      .date(donationAllocated.getCreatedDate())
      .shipmentDate(shipmentDate)
      .build();
  }

  @Override
  public DonationAllocated findById(Integer donationAllocatedId) {
    return donationAllocatedRepository
      .findById(donationAllocatedId)
      .orElseThrow(() -> new RuntimeException(
        "Cannot find donation allocated by ID of " + donationAllocatedId)); // i suck :(
  }
}
