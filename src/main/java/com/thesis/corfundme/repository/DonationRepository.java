package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<DonationActivity, Integer> {
  List<DonationActivity> findTop3ByStatusOrderByCreatedDateDesc(DonationStatus donationStatus);
  List<DonationActivity> findByStatus(DonationStatus donationStatus);
}
