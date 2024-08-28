package com.thesis.corfundme.request;

import lombok.Data;

@Data
public class EditUserRequest {
  private String username;
  private String email;
  private String password;
}
