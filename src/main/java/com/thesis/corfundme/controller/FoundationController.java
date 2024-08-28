package com.thesis.corfundme.controller;

import com.thesis.corfundme.request.CreateFoundationRequest;
import com.thesis.corfundme.response.BaseResponse;
import com.thesis.corfundme.response.CreateFoundationResponse;
import com.thesis.corfundme.service.IFoundationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/foundations")
public class FoundationController {
  @Autowired
  private IFoundationService foundationService;

  @PostMapping("/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public BaseResponse<CreateFoundationResponse> createFoundation(@PathVariable Integer userId,
    @RequestBody CreateFoundationRequest createFoundationRequest) {
    try {
      return BaseResponse.ok(foundationService.createFoundation(userId, createFoundationRequest));
    } catch (Exception e) {
      log.error("Create foundation error: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}
