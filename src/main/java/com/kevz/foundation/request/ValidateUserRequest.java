package com.kevz.foundation.request;

import lombok.Data;

@Data
public class ValidateUserRequest {
  private String email;
  private String password;
}
