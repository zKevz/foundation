package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocation;
import com.thesis.corfundme.model.DonationShipmentStatus;
import com.thesis.corfundme.model.DonationStatus;
import com.thesis.corfundme.model.Foundation;
import com.thesis.corfundme.repository.DonationAllocationRepository;
import com.thesis.corfundme.repository.DonationRepository;
import com.thesis.corfundme.request.CreateDonationRequest;
import com.thesis.corfundme.request.EditDonationRequest;
import com.thesis.corfundme.response.DonationAllocationDetailResponse;
import com.thesis.corfundme.response.DonationDetailResponse;
import com.thesis.corfundme.response.DonationHeaderResponse;
import com.thesis.corfundme.service.IDonationAllocatedService;
import com.thesis.corfundme.service.IDonationAllocationService;
import com.thesis.corfundme.service.IDonationService;
import com.thesis.corfundme.service.IFoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
      .total(donationAllocatedService.calculateAllocatedAmountSum(donationActivity))
      .remainingDays(getRemainingDonationDays(donationActivity))
      .build();
  }

  @Override
  @Transactional
  public void editDonation(Integer donationId, EditDonationRequest editDonationRequest) {
    DonationActivity donationActivity = findById(donationId);
    if (Objects.nonNull(editDonationRequest.getName())) {
      donationActivity.setName(editDonationRequest.getName());
    }

    if (Objects.nonNull(editDonationRequest.getEndDate())) {
      donationActivity.setEndDate(editDonationRequest.getEndDate());
    }

    if (Objects.nonNull(editDonationRequest.getImageUrl())) {
      donationActivity.setImageUrl(editDonationRequest.getImageUrl());
    }

    if (Objects.nonNull(editDonationRequest.getImageProofUrl())) {
      donationActivity.setImageProofUrl(editDonationRequest.getImageProofUrl());
    }

    if (Objects.nonNull(editDonationRequest.getDisasterName())) {
      donationActivity.setDisasterName(editDonationRequest.getDisasterName());
    }

    if (Objects.nonNull(editDonationRequest.getDisasterDescription())) {
      donationActivity.setDisasterDescription(editDonationRequest.getDisasterDescription());
    }

    if (Objects.nonNull(editDonationRequest.getStatus())) {
      donationActivity.setStatus(editDonationRequest.getStatus());
    }

    if (Objects.nonNull(editDonationRequest.getShipmentStatus())) {
      donationActivity.setShipmentStatus(editDonationRequest.getShipmentStatus());
    }

    if (Objects.nonNull(editDonationRequest.getAllocation())) {
      for (EditDonationRequest.DonationAllocationItem donationAllocationItem : editDonationRequest.getAllocation()) {
        DonationAllocation donationAllocation = donationAllocationService.findById(donationAllocationItem.getId());
        donationAllocation.setAmount(donationAllocationItem.getAmount());
        donationAllocation.setDescription(donationAllocationItem.getDescription());
      }
    }
  }

  @Override
  public DonationActivity findById(Integer donationId) {
    return donationRepository
      .findById(donationId)
      .orElseThrow(() -> new RuntimeException("Cannot find donation activity of ID: " + donationId));
  }
}
