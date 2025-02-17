package com.kevz.foundation.model;

import lombok.Getter;

@Getter
public enum RefundStatus {
  OPEN("Proses Pengajuan Pengembalian Dana"),
  ACCEPTED("Pengembalian Dana Diterima"),
  REJECTED("Penolakan Pengembalian Dana");

  private final String value;

  RefundStatus(String value) {
    this.value = value;
  }
}
