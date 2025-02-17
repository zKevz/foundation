package com.kevz.foundation.repository;

import com.kevz.foundation.model.DonationActivity;
import com.kevz.foundation.model.DonationAllocated;
import com.kevz.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationAllocatedRepository extends JpaRepository<DonationAllocated, Integer> {
  List<DonationAllocated> findByDonationActivityAndDeletedFalse(DonationActivity donationActivity);

  List<DonationAllocated> findByUserAndDeletedFalse(User user);
  Optional<DonationAllocated> findByUserAndIdAndDeletedFalse(User user, Integer id);
}
