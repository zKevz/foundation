package com.thesis.corfundme.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class DonationAllocationDetailResponse {
  private List<DonationAllocationDetailItemResponse> items;
  private Long remainingDays;
  private Integer total;

  @Data
  @SuperBuilder
  public static class DonationAllocationDetailItemResponse {
    private String description;
    private Integer amount;
  }
}
