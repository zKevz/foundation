package com.thesis.corfundme.scheduler;

import com.thesis.corfundme.model.Refund;
import com.thesis.corfundme.model.RefundStatus;
import com.thesis.corfundme.repository.RefundRepository;
import com.thesis.corfundme.service.IRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class RefundScheduler {
  @Autowired
  private RefundRepository refundRepository;

  @Autowired
  private IRefundService refundService;

  @Scheduled(fixedRate = 60000) // 1 minute
  public void run() {
    List<Refund> refunds =
      refundRepository.findByStatusAndCreatedDateBefore(RefundStatus.OPEN, LocalDateTime.now().minusHours(24));
    for (Refund refund : refunds) {
      log.info("Automatically approve refund for ID {}", refund.getId());
      refundService.approveRefund(refund.getId());
    }
  }
}
