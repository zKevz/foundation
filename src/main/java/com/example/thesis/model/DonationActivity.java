package com.example.thesis.model;

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

import java.util.Date;

@Data
@Table
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonationActivity extends BaseModel {
  @Column
  private String name;

  @Column
  private Date endDate;

  @Column
  private String imageUrl;

  @Column
  private String imageProofUrl;

  @Column
  private String disasterName; // lol..

  @Column
  private String disasterDescription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Foundation foundation;

  @Column
  private DonationStatus status;

  @Column
  private DonationShipmentStatus shipmentStatus;
}
