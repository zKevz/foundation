package com.kevz.foundation.model;

import lombok.Getter;

@Getter
public enum DonationStatus {
  OPEN("Buka"), CLOSED("Tutup");

  private final String value;

  DonationStatus(String value) {
    this.value = value;
  }
}
