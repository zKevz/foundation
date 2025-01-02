package com.thesis.corfundme.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class DonationAllocatedDetailResponse {
  private Integer id;
  private Integer amount;
  private Long remainingDays;
  private String name;
  private String refundReason;
  private String proofImageUrl;
  private String foundationName;
  private String refundRejectReason;
  private String refundStatus;
  private String activityStatus;
  private String shipmentStatus;
  private Date date;
  private Date shipmentDate;
}
