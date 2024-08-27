package com.example.thesis.response;

import com.example.thesis.model.DonationAllocatedStatus;
import com.example.thesis.model.DonationShipmentStatus;
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
