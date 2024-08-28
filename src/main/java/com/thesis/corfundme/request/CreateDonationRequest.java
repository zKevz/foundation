package com.thesis.corfundme.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateDonationRequest {
  private String name;
  private String imageUrl;
  private String description;
  private Long endDateUnixTimestamp;
  private List<DonationAllocationItem> allocation;

  @Data
  public static class DonationAllocationItem {
    private String description;
    private Integer amount;
  }
}
