package com.kevz.foundation.controller;

import com.kevz.foundation.request.CreateDonationRequest;
import com.kevz.foundation.request.EditDonationRequest;
import com.kevz.foundation.response.BaseResponse;
import com.kevz.foundation.response.DonationAllocationDetailResponse;
import com.kevz.foundation.response.DonationDetailResponse;
import com.kevz.foundation.response.DonationHeaderResponse;
import com.kevz.foundation.service.IDonationService;
import com.kevz.foundation.service.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {
  @Autowired
  private IDonationService donationService;

  @Autowired
  private IStorageService storageService;

  @GetMapping("/newest")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<List<DonationHeaderResponse>> getNewestDonations() {
    try {
      return BaseResponse.ok(donationService.getNewestDonations());
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<List<DonationHeaderResponse>> getAllDonations() {
    try {
      return BaseResponse.ok(donationService.getAll());
    } catch (Exception e) {
      log.error("Get all donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{foundationId}")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<Object> createDonation(@PathVariable Integer foundationId,
    @RequestBody CreateDonationRequest createDonationRequest) {
    try {
      donationService.create(foundationId, createDonationRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Create donation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationId}")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<DonationDetailResponse> getDetail(@PathVariable Integer donationId) {
    try {
      return BaseResponse.ok(donationService.getDetail(donationId));
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{donationId}/allocation")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<DonationAllocationDetailResponse> getAllocationDetail(@PathVariable Integer donationId) {
    try {
      return BaseResponse.ok(donationService.getAllocationDetail(donationId));
    } catch (Exception e) {
      log.error("Get newest donations error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PutMapping("/{donationId}")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<Object> editDonation(@PathVariable Integer donationId,
    @RequestBody EditDonationRequest editDonationRequest) {
    try {
      donationService.editDonation(donationId, editDonationRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Edit donation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{donationId}/image")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<?> uploadImage(@PathVariable Integer donationId, @RequestParam("file") MultipartFile file) {
    try {
      donationService.uploadImage(donationId, file);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Upload donation image error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{donationId}/image-proof")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<?> uploadImageProof(@PathVariable Integer donationId, @RequestParam("file") MultipartFile file) {
    try {
      donationService.uploadImageProof(donationId, file);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Upload donation image proof error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping(value = "/image/{filename:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @ResponseBody
  public ResponseEntity<Resource> getDonationImage(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity
      .ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
      .contentType(MediaType.IMAGE_PNG)
      .body(file);
  }

  @PostMapping("/{donationId}/allocation")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<?> addDonationAllocation(@PathVariable Integer donationId,
    @RequestBody CreateDonationRequest.DonationAllocationItem addDonationAllocationRequest) {
    try {
      donationService.addDonationAllocation(donationId, addDonationAllocationRequest);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Upload donation image proof error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @DeleteMapping("/{donationId}/allocation/{allocationId}")
  @PreAuthorize("hasRole('FOUNDATION')")
  public BaseResponse<?> removeDonationAllocation(@PathVariable Integer donationId,
    @PathVariable Integer allocationId) {
    try {
      donationService.removeDonationAllocation(donationId, allocationId);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Upload donation image proof error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
