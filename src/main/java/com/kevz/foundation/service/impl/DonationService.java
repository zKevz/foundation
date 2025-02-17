package com.kevz.foundation.service.impl;

import com.kevz.foundation.model.Foundation;
import com.kevz.foundation.request.CreateDonationRequest;
import com.kevz.foundation.request.EditDonationRequest;
import com.kevz.foundation.service.IDonationAllocatedService;
import com.kevz.foundation.service.IDonationAllocationService;
import com.kevz.foundation.service.IDonationService;
import com.kevz.foundation.service.IFoundationService;
import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocation;
import com.kevz.foundation.model.DonationShipmentStatus;
import com.kevz.foundation.model.DonationStatus;
import com.kevz.foundation.repository.DonationAllocationRepository;
import com.kevz.foundation.repository.DonationRepository;
import com.kevz.foundation.response.DonationAllocationDetailResponse;
import com.kevz.foundation.response.DonationDetailResponse;
import com.kevz.foundation.response.DonationHeaderResponse;
import com.kevz.foundation.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

  @Autowired
  private IStorageService storageService;

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
      .foundationEmail(foundation.getUser().getEmail())
      .foundationAddress(foundation.getAddress())
      .allocatedAmount(donationAllocatedService.calculateAllocatedAmountSum(donationActivity))
      .dateArrived(donationActivity.getArrivedDate())
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
      donationAllocationRepository.findByDonationActivityAndDeletedFalse(donationActivity);
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

    if (Objects.nonNull(editDonationRequest.getDisasterDescription())) {
      donationActivity.setDisasterDescription(editDonationRequest.getDisasterDescription());
    }

    if (Objects.nonNull(editDonationRequest.getStatus())) {
      donationActivity.setStatus(editDonationRequest.getStatus());
    }

    if (Objects.nonNull(editDonationRequest.getShipmentStatus())) {
      donationActivity.setShipmentStatus(editDonationRequest.getShipmentStatus());

      if (DonationShipmentStatus.ARRIVED.equals(editDonationRequest.getShipmentStatus())) {
        donationActivity.setArrivedDate(new Date());
      } else if (DonationShipmentStatus.ON_DELIVER.equals(editDonationRequest.getShipmentStatus())) {
        donationActivity.setDeliveredDate(new Date());
      }
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
  @Transactional
  public void uploadImage(Integer donationId, MultipartFile file) {
    DonationActivity donationActivity = findById(donationId);
    storageService.store(donationActivity.getId().toString(), file);
    donationActivity.setImageUrl("http://localhost:8080/api/v1/donations/image/" + donationActivity.getId()
      + storageService.getFileExtension(file.getOriginalFilename()));
  }

  @Override
  @Transactional
  public void uploadImageProof(Integer donationId, MultipartFile file) {
    DonationActivity donationActivity = findById(donationId);
    String filename = donationActivity.getId() + "_proof";
    storageService.store(filename, file);
    donationActivity.setImageProofUrl("http://localhost:8080/api/v1/donations/image/" + filename
      + storageService.getFileExtension(file.getOriginalFilename()));
  }

  @Override
  @Transactional
  public void addDonationAllocation(Integer donationId,
    CreateDonationRequest.DonationAllocationItem addDonationAllocationRequest) {
    DonationActivity donationActivity = findById(donationId);
    DonationAllocation donationAllocation = DonationAllocation
      .builder()
      .donationActivity(donationActivity)
      .description(addDonationAllocationRequest.getDescription())
      .amount(addDonationAllocationRequest.getAmount())
      .build();
    donationAllocationRepository.save(donationAllocation);
  }

  @Override
  @Transactional
  public void removeDonationAllocation(Integer donationId, Integer allocationId) {
    DonationActivity donationActivity = findById(donationId);
    DonationAllocation donationAllocation = donationAllocationService.findById(allocationId);
    donationAllocation.setDeleted(true);
  }

  @Override
  public DonationActivity findById(Integer donationId) {
    return donationRepository
      .findById(donationId)
      .orElseThrow(() -> new RuntimeException("Cannot find donation activity of ID: " + donationId));
  }
}
