package com.thesis.corfundme.response;

import com.thesis.corfundme.model.DonationAllocatedStatus;
import com.thesis.corfundme.model.DonationShipmentStatus;
import com.thesis.corfundme.model.DonationStatus;
import com.thesis.corfundme.model.RefundStatus;
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
  private RefundStatus refundStatus;
  private DonationStatus activityStatus;
  private DonationShipmentStatus shipmentStatus;
  private DonationAllocatedStatus allocatedStatus;
  private Date date;
  private Date shipmentDate;
}
