package com.kevz.foundation.service;

import com.kevz.foundation.model.Refund;
import com.kevz.foundation.model.User;
import com.kevz.foundation.response.RefundDetailResponse;
import com.kevz.foundation.response.RefundHeaderResponse;

import java.util.List;

public interface IRefundService {
  void requestRefund(Integer donationAllocatedId, User user, String reason);

  void approveRefund(Integer refundId);

  void rejectRefund(Integer refundId, String reason);

  Refund findById(Integer refundId);

  List<RefundHeaderResponse> getRefunds(User user);
  RefundDetailResponse getRefundDetail(Integer refundId, User user);
}
