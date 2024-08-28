package com.example.thesis.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateFoundationRequest {
  private String address;
  private String imageUrl;
}
