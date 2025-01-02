package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.Refund;
import com.thesis.corfundme.model.RefundStatus;
import com.thesis.corfundme.model.User;
import com.thesis.corfundme.repository.DonationAllocatedRepository;
import com.thesis.corfundme.repository.RefundRepository;
import com.thesis.corfundme.response.DonationAllocatedDetailResponse;
import com.thesis.corfundme.response.DonationAllocatedHeaderResponse;
import com.thesis.corfundme.service.IDonationAllocatedService;
import com.thesis.corfundme.service.IDonationService;
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
