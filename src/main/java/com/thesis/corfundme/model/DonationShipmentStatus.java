package com.thesis.corfundme.model;

import lombok.Getter;

@Getter
public enum DonationShipmentStatus {
  NOT_DELIVERED("Belum dalam pengiriman"),
  ON_DELIVER("Sedang dalam pengiriman"),
  ARRIVED("Sudah tiba tujuan");


  private final String value;

  DonationShipmentStatus(String value) {
    this.value = value;
  }
}
