package com.example.thesis.repository;

import com.example.thesis.model.DonationActivity;
import com.example.thesis.model.DonationAllocated;
import com.example.thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationAllocatedRepository extends JpaRepository<DonationAllocated, Integer> {
  List<DonationAllocated> findByDonationActivity(DonationActivity donationActivity);

  List<DonationAllocated> findByUser(User user);
  DonationAllocated findByUserAndId(User user, Integer id);
}
