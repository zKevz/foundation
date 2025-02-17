package com.kevz.foundation.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidateUserResponse {
  private Integer userId;
  private String username;
  private String accessToken;
  private String refreshToken;
}
