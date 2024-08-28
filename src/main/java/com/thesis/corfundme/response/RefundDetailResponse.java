package com.thesis.corfundme.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefundDetailResponse extends RefundHeaderResponse {
  private String reason;
  private String username; // the user that did the refund
}
