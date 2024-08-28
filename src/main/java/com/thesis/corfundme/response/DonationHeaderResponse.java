package com.thesis.corfundme.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DonationHeaderResponse {
  private Integer id;
  private String name;
  private String imageUrl;
  private String foundationName;
  private Integer amount;
  private Integer allocatedAmount;
  private Long remainingDays;
}
