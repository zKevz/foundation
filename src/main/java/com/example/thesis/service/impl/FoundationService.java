package com.example.thesis.service.impl;

import com.example.thesis.model.Foundation;
import com.example.thesis.model.User;
import com.example.thesis.model.UserRole;
import com.example.thesis.repository.FoundationRepository;
import com.example.thesis.request.CreateFoundationRequest;
import com.example.thesis.response.CreateFoundationResponse;
import com.example.thesis.service.IFoundationService;
import com.example.thesis.service.IUserService;
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
        "Cannot find foundation? This shouldn't happen though, if it does then me suck looool"));
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
