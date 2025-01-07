package com.thesis.corfundme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonationAllocation extends BaseModel {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private DonationActivity donationActivity;

  @Column
  private String description;

  @Column
  private Integer amount;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean deleted;
}
