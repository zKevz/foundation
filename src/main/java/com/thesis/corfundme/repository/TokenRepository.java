package com.thesis.corfundme.repository;

import com.thesis.corfundme.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  List<Token> findByUserIdAndRevokedFalseAndExpiredFalse(Integer userId);
  Optional<Token> findByTokenAndRevokedFalseAndExpiredFalse(String token);
  Optional<Token> findByToken(String token);

}
