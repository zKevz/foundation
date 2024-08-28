package com.thesis.corfundme.response;

import com.thesis.corfundme.model.DonationAllocatedStatus;
import com.thesis.corfundme.model.DonationShipmentStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class DonationAllocatedDetailResponse {
  private Date date;
  private Integer id;
  private Integer amount;
  private Long remainingDays;
  private String name;
  private String proofImageUrl;
  private String foundationName;
  private DonationAllocatedStatus donationStatus;
  private DonationShipmentStatus donationShipmentStatus;
}
