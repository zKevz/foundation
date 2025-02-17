package com.kevz.foundation.repository;

import com.kevz.foundation.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  List<Token> findByUserIdAndRevokedFalseAndExpiredFalse(Integer userId);
  Optional<Token> findByTokenAndRevokedFalseAndExpiredFalse(String token);
  Optional<Token> findByToken(String token);

}
