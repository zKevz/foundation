package com.example.thesis.request;

import lombok.Data;

@Data
public class DonationPayRequest {
  private Integer amount;
  private String paymentType;
}
