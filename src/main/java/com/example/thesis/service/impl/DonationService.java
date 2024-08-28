package com.example.thesis.service.impl;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocation;
import com.example.thesis.model.DonationShipmentStatus;
import com.example.thesis.model.DonationStatus;
import com.example.thesis.model.Foundation;
import com.example.thesis.repository.DonationAllocationRepository;
import com.example.thesis.repository.DonationRepository;
import com.example.thesis.request.CreateDonationRequest;
import com.example.thesis.response.DonationAllocationDetailResponse;
import com.example.thesis.response.DonationDetailResponse;
import com.example.thesis.response.DonationHeaderResponse;
import com.example.thesis.service.IDonationAllocatedService;
import com.example.thesis.service.IDonationAllocationService;
import com.example.thesis.service.IDonationService;
import com.example.thesis.service.IFoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService implements IDonationService {

  @Autowired
  private DonationRepository donationRepository;

  @Autowired
  private IFoundationService foundationService;

  @Autowired
  private DonationAllocationRepository donationAllocationRepository;

  @Autowired
  private IDonationAllocatedService donationAllocatedService;

  @Autowired
  private IDonationAllocationService donationAllocationService;

  @Override
  public Long getRemainingDonationDays(DonationActivity donationActivity) {
    LocalDate localDate = LocalDate.now();
    LocalDate localEndDate = donationActivity.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return ChronoUnit.DAYS.between(localDate, localEndDate);
  }

  private DonationHeaderResponse mapDonationActivityToHeaderResponse(DonationActivity donationActivity) {
    Foundation foundation = donationActivity.getFoundation();

    return DonationHeaderResponse
      .builder()
      .id(donationActivity.getId())
      .name(donationActivity.getName())
      .amount(donationAllocationService.calculateAllocationAmountSum(donationActivity))
      .imageUrl(donationActivity.getImageUrl())
      .remainingDays(getRemainingDonationDays(donationActivity))
      .foundationName(foundation.getUser().getName())
      .allocatedAmount(donationAllocatedService.calculateAllocatedAmountSum(donationActivity))
      .build();
  }

  private DonationDetailResponse mapDonationActivityToDetailResponse(DonationActivity donationActivity) {
    Foundation foundation = donationActivity.getFoundation();

    return DonationDetailResponse
      .builder()
      .id(donationActivity.getId())
      .name(donationActivity.getName())
      .amount(donationAllocationService.calculateAllocationAmountSum(donationActivity))
      .imageUrl(donationActivity.getImageUrl())
      .description(donationActivity.getDisasterDescription())
      .remainingDays(getRemainingDonationDays(donationActivity))
      .foundationName(foundation.getUser().getName())
      .allocatedAmount(donationAllocatedService.calculateAllocatedAmountSum(donationActivity))
      .build();
  }

  @Override
  public List<DonationHeaderResponse> getNewestDonations() {
    return donationRepository
      .findTop3ByStatusOrderByCreatedDateDesc(DonationStatus.OPEN)
      .stream()
      .map(this::mapDonationActivityToHeaderResponse)
      .collect(Collectors.toList());
  }

  @Override
  public List<DonationHeaderResponse> getAll() {
    return donationRepository
      .findByStatus(DonationStatus.OPEN)
      .stream()
      .map(this::mapDonationActivityToHeaderResponse)
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void create(Integer foundationId, CreateDonationRequest createDonationRequest) {
    DonationActivity donationActivity = DonationActivity
      .builder()
      .name(createDonationRequest.getName())
      .imageUrl(createDonationRequest.getImageUrl())
      .disasterName(createDonationRequest.getName())
      .disasterDescription(createDonationRequest.getDescription())
      .foundation(foundationService.findById(foundationId))
      .status(DonationStatus.OPEN)
      .endDate(new Date(createDonationRequest.getEndDateUnixTimestamp() * 1000))
      .shipmentStatus(DonationShipmentStatus.NOT_DELIVERED)
      .build();
    donationRepository.save(donationActivity);

    for (CreateDonationRequest.DonationAllocationItem donationAllocationItem : createDonationRequest.getAllocation()) {
      DonationAllocation donationAllocation = DonationAllocation
        .builder()
        .amount(donationAllocationItem.getAmount())
        .description(donationAllocationItem.getDescription())
        .donationActivity(donationActivity)
        .build();
      donationAllocationRepository.save(donationAllocation);
    }
  }

  @Override
  public DonationDetailResponse getDetail(Integer donationId) {
    return donationRepository
      .findById(donationId)
      .map(this::mapDonationActivityToDetailResponse)
      .orElseThrow(() -> new RuntimeException("Cannot find donation ID: " + donationId));
  }

  @Override
  public DonationAllocationDetailResponse getAllocationDetail(Integer donationId) {
    DonationActivity donationActivity = findById(donationId);
    List<DonationAllocation> donationAllocations =
      donationAllocationRepository.findByDonationActivity(donationActivity);
    List<DonationAllocationDetailResponse.DonationAllocationDetailItemResponse> donationAllocationItems =
      donationAllocations
        .stream()
        .map(donationAllocation -> DonationAllocationDetailResponse.DonationAllocationDetailItemResponse
          .builder()
          .amount(donationAllocation.getAmount())
          .description(donationAllocation.getDescription())
          .build())
        .collect(Collectors.toList());

    return DonationAllocationDetailResponse
      .builder()
      .items(donationAllocationItems)
      .remainingDays(getRemainingDonationDays(donationActivity))
      .build();
  }

  @Override
  @Transactional
  public void uploadProofImageUrl(Integer donationId, String url) {
    DonationActivity donationActivity = findById(donationId);
    donationActivity.setImageProofUrl(url);
  }

  @Override
  public DonationActivity findById(Integer donationId) {
    return donationRepository
      .findById(donationId)
      .orElseThrow(() -> new RuntimeException("Cannot find donation activity of ID: " + donationId));
  }
}
