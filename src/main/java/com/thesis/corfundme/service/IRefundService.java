package com.thesis.corfundme.service;

import com.thesis.corfundme.model.Refund;
import com.thesis.corfundme.model.User;
import com.thesis.corfundme.response.RefundDetailResponse;
import com.thesis.corfundme.response.RefundHeaderResponse;

import java.util.List;

public interface IRefundService {
  void requestRefund(Integer donationAllocatedId, User user, String reason);

  void approveRefund(Integer refundId);

  void rejectRefund(Integer refundId);

  Refund findById(Integer refundId);

  List<RefundHeaderResponse> getRefunds(User user);
  RefundDetailResponse getRefundDetail(Integer refundId, User user);
}
