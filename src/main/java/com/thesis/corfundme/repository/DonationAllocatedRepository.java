package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.DonationActivity;
import com.thesis.corfundme.model.DonationAllocated;
import com.thesis.corfundme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationAllocatedRepository extends JpaRepository<DonationAllocated, Integer> {
  List<DonationAllocated> findByDonationActivityAndDeletedFalse(DonationActivity donationActivity);

  List<DonationAllocated> findByUserAndDeletedFalse(User user);
  Optional<DonationAllocated> findByUserAndIdAndDeletedFalse(User user, Integer id);
}
