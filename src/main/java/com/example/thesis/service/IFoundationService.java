package com.example.thesis.service;

import com.example.thesis.model.Foundation;
import com.example.thesis.request.CreateFoundationRequest;
import com.example.thesis.response.CreateFoundationResponse;

public interface IFoundationService {
  Foundation findById(Integer foundationId);

  CreateFoundationResponse createFoundation(Integer userId, CreateFoundationRequest createFoundationRequest);
}
