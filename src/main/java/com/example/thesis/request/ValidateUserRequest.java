package com.example.thesis.request;

import lombok.Data;

@Data
public class ValidateUserRequest {
  private String email;
  private String password;
}
