package com.thesis.corfundme.response;

import com.thesis.corfundme.model.DonationAllocatedStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class DonationAllocatedHeaderResponse {
  private Integer id;
  private String name;
  private String imageUrl;
  private String foundationName;
  private Integer amount;
  private DonationAllocatedStatus status;
  private Date date;
}
