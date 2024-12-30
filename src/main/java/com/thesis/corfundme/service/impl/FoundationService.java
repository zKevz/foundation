package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.model.Foundation;
import com.thesis.corfundme.model.UserRole;
import com.thesis.corfundme.repository.FoundationRepository;
import com.thesis.corfundme.request.CreateFoundationRequest;
import com.thesis.corfundme.response.CreateFoundationResponse;
import com.thesis.corfundme.service.IFoundationService;
import com.thesis.corfundme.service.IUserService;
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
