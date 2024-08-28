package com.thesis.corfundme.service;

import com.thesis.corfundme.model.Foundation;
import com.thesis.corfundme.request.CreateFoundationRequest;
import com.thesis.corfundme.response.CreateFoundationResponse;

public interface IFoundationService {
  Foundation findById(Integer foundationId);

  CreateFoundationResponse createFoundation(Integer userId, CreateFoundationRequest createFoundationRequest);
}
