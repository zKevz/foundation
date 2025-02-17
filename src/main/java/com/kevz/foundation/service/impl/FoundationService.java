package com.kevz.foundation.service.impl;

import com.kevz.foundation.model.Foundation;
import com.kevz.foundation.request.CreateFoundationRequest;
import com.kevz.foundation.service.IFoundationService;
import com.kevz.foundation.service.IUserService;
import com.kevz.foundation.model.UserRole;
import com.kevz.foundation.repository.FoundationRepository;
import com.kevz.foundation.response.CreateFoundationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoundationService implements IFoundationService {
  @Autowired
  private FoundationRepository foundationRepository;

  @Autowired
  private IUserService userService;

  @Override
  public Foundation findById(Integer foundationId) {
    return foundationRepository
      .findById(foundationId)
      .orElseThrow(() -> new RuntimeException(
        "Cannot find foundation " + foundationId + "? This shouldn't happen though, if it does then me suck looool"));
  }

  @Override
  @Transactional
  public CreateFoundationResponse createFoundation(Integer userId, CreateFoundationRequest createFoundationRequest) {
    userService.changeUserRole(userId, UserRole.FOUNDATION);

    Foundation foundation = foundationRepository.save(Foundation
      .builder()
      .user(userService.findById(userId))
      .address(createFoundationRequest.getAddress())
      .imageUrl(createFoundationRequest.getImageUrl())
      .build());

    return CreateFoundationResponse.builder().foundationId(foundation.getId()).build();
  }
}
