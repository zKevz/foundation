package com.thesis.corfundme.request;

import lombok.Data;

@Data
public class ValidateUserRequest {
  private String email;
  private String password;
}
