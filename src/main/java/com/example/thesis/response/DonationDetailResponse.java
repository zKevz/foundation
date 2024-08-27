package com.example.thesis.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DonationDetailResponse {
  private String name;
  private String imageUrl;
  private String description;
  private String foundationName;
  private Integer amount;
  private Integer allocatedAmount;
  private Long remainingDays;
}
