package com.thesis.corfundme.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class DonationDetailResponse {
  private Integer id;
  private String name;
  private String imageUrl;
  private String description;
  private String foundationName;
  private Integer amount;
  private Integer allocatedAmount;
  private Long remainingDays;
  private Date dateArrived;
}
