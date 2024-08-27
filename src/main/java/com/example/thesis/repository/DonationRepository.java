package com.example.thesis.repository;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<DonationActivity, Integer> {
  List<DonationActivity> findTop3ByStatusOrderByCreatedDateDesc(DonationStatus donationStatus);
  List<DonationActivity> findByStatus(DonationStatus donationStatus);
}
