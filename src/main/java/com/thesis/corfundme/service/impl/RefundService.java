package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.DonationAllocatedStatus;
import com.thesis.corfundme.model.Refund;
import com.thesis.corfundme.model.RefundStatus;
import com.thesis.corfundme.model.User;
import com.thesis.corfundme.repository.RefundRepository;
import com.thesis.corfundme.response.RefundDetailResponse;
import com.thesis.corfundme.response.RefundHeaderResponse;
import com.thesis.corfundme.service.IDonationAllocatedService;
import com.thesis.corfundme.service.IRefundService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefundService implements IRefundService {
  @Autowired
  private IDonationAllocatedService donationAllocatedService;

  @Autowired
  private RefundRepository refundRepository;

  @Override
  @Transactional
  public void requestRefund(Integer donationAllocatedId, User user, String reason) {
    DonationAllocated donationAllocated = donationAllocatedService.findById(donationAllocatedId);
    donationAllocated.setStatus(DonationAllocatedStatus.WAITING_FOR_REFUND_APPROVAL);

    Refund refund =
      Refund.builder().user(user).status(RefundStatus.OPEN).reason(reason).donationAllocated(donationAllocated).build();
    refundRepository.save(refund);
  }

  @Override
  @Transactional
  public void approveRefund(Integer refundId) {
    Refund refund = findById(refundId);
    refund.setStatus(RefundStatus.ACCEPTED);
  }

  @Override
  @Transactional
  public void rejectRefund(Integer refundId, String reason) {
    Refund refund = findById(refundId);
    refund.setStatus(RefundStatus.REJECTED);
    refund.setRejectReason(reason);
  }

  private RefundHeaderResponse mapRefundToHeaderResponse(Refund refund) {
    return RefundHeaderResponse
      .builder()
      .id(refund.getId())
      .name(refund.getDonationAllocated().getDonationActivity().getName())
      .foundationName(refund.getDonationAllocated().getDonationActivity().getFoundation().getUser().getName())
      .amount(refund.getDonationAllocated().getAmount())
      .imageUrl(refund.getDonationAllocated().getDonationActivity().getImageUrl())
      .status(refund.getStatus())
      .date(refund.getCreatedDate())
      .build();
  }

  private RefundDetailResponse mapRefundToDetailResponse(Refund refund) {
    RefundHeaderResponse refundHeaderResponse = mapRefundToHeaderResponse(refund);
    RefundDetailResponse refundDetailResponse = new RefundDetailResponse();
    BeanUtils.copyProperties(refundHeaderResponse, refundDetailResponse);
    refundDetailResponse.setReason(refund.getReason());
    refundDetailResponse.setUsername(refund.getUser().getName());
    return refundDetailResponse;
  }

  @Override
  public Refund findById(Integer refundId) {
    return refundRepository
      .findById(refundId)
      .orElseThrow(() -> new RuntimeException("Cannot find refund by ID of " + refundId));
  }

  @Override
  public List<RefundHeaderResponse> getRefunds(User user) {
    return refundRepository.findAll().stream().map(this::mapRefundToHeaderResponse).collect(Collectors.toList());
  }

  @Override
  public RefundDetailResponse getRefundDetail(Integer refundId, User user) {
    return mapRefundToDetailResponse(refundRepository
      .findByIdAndUser(refundId, user)
      .orElseThrow(() -> new RuntimeException(
        "Cannot find refund by ID of " + refundId + " and user id of " + user.getId())));
  }
}
