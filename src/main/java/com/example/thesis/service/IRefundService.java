package com.example.thesis.service;

import com.example.thesis.model.Refund;
import com.example.thesis.model.User;
import com.example.thesis.response.RefundDetailResponse;
import com.example.thesis.response.RefundHeaderResponse;

import java.util.List;

public interface IRefundService {
  void requestRefund(Integer donationAllocatedId, User user, String reason);

  void approveRefund(Integer refundId);

  void rejectRefund(Integer refundId);

  Refund findById(Integer refundId);

  List<RefundHeaderResponse> getRefunds(User user);
  RefundDetailResponse getRefundDetail(Integer refundId, User user);
}
