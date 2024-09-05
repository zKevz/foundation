package com.thesis.corfundme.request;

import com.thesis.corfundme.model.DonationShipmentStatus;
import com.thesis.corfundme.model.DonationStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditDonationRequest {
  private String name;
  private Date endDate;
  private Date arrivedDate;
  private String imageUrl;
  private String imageProofUrl;
  private String disasterName;
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
