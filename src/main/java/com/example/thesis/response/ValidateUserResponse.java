package com.example.thesis.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidateUserResponse {
  private Integer userId;
  private String accessToken;
  private String refreshToken;
}
