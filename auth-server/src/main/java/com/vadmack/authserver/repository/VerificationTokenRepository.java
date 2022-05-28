package com.vadmack.authserver.repository;

import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.domain.entity.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByTokenAndTokenType(String token, TokenType tokenType);
}
