package com.example.thesis.request;

import lombok.Data;

@Data
public class EditUserRequest {
  private String username;
  private String email;
  private String password;
}
