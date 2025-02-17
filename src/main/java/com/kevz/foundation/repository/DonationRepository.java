package com.kevz.foundation.repository;

import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<DonationActivity, Integer> {
  List<DonationActivity> findTop3ByStatusOrderByCreatedDateDesc(DonationStatus donationStatus);
  List<DonationActivity> findByStatus(DonationStatus donationStatus);
}
