package com.kevz.foundation.response;

import com.kevz.foundation.model.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
public class RefundHeaderResponse {
  private Integer id;
  private Integer amount;
  private String name;
  private String imageUrl;
  private String foundationName;
  private RefundStatus status;
  private Date date;
}
