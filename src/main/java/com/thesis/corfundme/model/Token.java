package com.thesis.corfundme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Table
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseModel {
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private User user;

  @Column
  public String token;

  @Column
  public boolean revoked;

  @Column
  public boolean expired;
}
