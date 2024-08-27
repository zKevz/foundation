package com.example.thesis.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DonationHeaderResponse {
  private String name;
  private String imageUrl;
  private String foundationName;
  private Integer amount;
  private Integer allocatedAmount;
  private Long remainingDays;
}
