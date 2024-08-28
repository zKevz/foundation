package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationAllocatedRepository extends JpaRepository<DonationAllocated, Integer> {
  List<DonationAllocated> findByDonationActivity(DonationActivity donationActivity);

  List<DonationAllocated> findByUser(User user);
  Optional<DonationAllocated> findByUserAndId(User user, Integer id);
}
