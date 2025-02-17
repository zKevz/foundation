package com.kevz.foundation.request;

import com.kevz.foundation.model.DonationShipmentStatus;
import com.kevz.foundation.model.DonationStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditDonationRequest {
  private String name;
  private Date endDate;
  private String disasterDescription;
  private DonationStatus status;
  private DonationShipmentStatus shipmentStatus;
  private List<DonationAllocationItem> allocation;

  @Data
  public static class DonationAllocationItem {
    private Integer id;
    private String description;
    private Integer amount;
  }

}
