package com.kevz.foundation.service;

import com.kevz.foundation.model.Foundation;
import com.kevz.foundation.request.CreateFoundationRequest;
import com.kevz.foundation.response.CreateFoundationResponse;

public interface IFoundationService {
  Foundation findById(Integer foundationId);

  CreateFoundationResponse createFoundation(Integer userId, CreateFoundationRequest createFoundationRequest);
}
