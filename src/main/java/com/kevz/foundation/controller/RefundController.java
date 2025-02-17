package com.kevz.foundation.controller;

import com.kevz.foundation.request.RejectRefundRequest;
import com.kevz.foundation.request.RequestRefundRequest;
import com.kevz.foundation.response.BaseResponse;
import com.kevz.foundation.response.RefundDetailResponse;
import com.kevz.foundation.response.RefundHeaderResponse;
import com.kevz.foundation.service.IRefundService;
import com.kevz.foundation.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/refund")
public class RefundController {
  @Autowired
  private IRefundService refundService;

  @PostMapping("/{donationAllocatedId}")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<Object> requestRefund(@AuthenticationPrincipal User user,
    @PathVariable Integer donationAllocatedId,
    @RequestBody RequestRefundRequest requestRefundRequest) {
    try {
      refundService.requestRefund(donationAllocatedId, user, requestRefundRequest.getReason());
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Request refund error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<List<RefundHeaderResponse>> getRefunds(@AuthenticationPrincipal User user) {
    try {
      return BaseResponse.ok(refundService.getRefunds(user));
    } catch (Exception e) {
      log.error("Get refunds error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @GetMapping("/{refundId}")
  @PreAuthorize("hasRole('USER')")
  public BaseResponse<RefundDetailResponse> getRefundDetail(@AuthenticationPrincipal User user,
    @PathVariable Integer refundId) {
    try {
      return BaseResponse.ok(refundService.getRefundDetail(refundId, user));
    } catch (Exception e) {
      log.error("Get refund detail error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{refundId}/_approve")
  @PreAuthorize("hasRole('ADMIN')")
  public BaseResponse<RefundDetailResponse> approveRefund(@PathVariable Integer refundId) {
    try {
      refundService.approveRefund(refundId);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Approve refund error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/{refundId}/_reject")
  @PreAuthorize("hasRole('ADMIN')")
  public BaseResponse<RefundDetailResponse> rejectRefund(@PathVariable Integer refundId,
    @RequestBody RejectRefundRequest rejectRefundRequest) {
    try {
      refundService.rejectRefund(refundId, rejectRefundRequest.getReason());
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Reject refund error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
