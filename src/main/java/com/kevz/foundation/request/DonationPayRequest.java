package com.kevz.foundation.request;

import lombok.Data;

@Data
public class DonationPayRequest {
  private Integer amount;
  private String paymentType;
}
