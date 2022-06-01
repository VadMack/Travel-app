package com.vadmack.authserver.service;

import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.VerificationToken;
import com.vadmack.authserver.exception.NotFoundException;
import com.vadmack.authserver.exception.ValidationException;
import com.vadmack.authserver.repository.VerificationTokenRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class VerificationTokenService {

    @Value("${refresh-token.expiration-in-minutes}")
    private Integer refreshTokenExpiration;

    @Value("${registration-token.expiration-in-minutes}")
    private Integer registrationTokenExpiration;

    @Value("${password-reset-token.expiration-in-minutes}")
    private Integer passwordResetTokenExpiration;

    private final VerificationTokenRepository verificationTokenRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private Map<TokenType, Integer> typeToExpiration = new HashMap<>();

    @PostConstruct
    private void postConstruct(){
        typeToExpiration = new HashMap<>();
        typeToExpiration.put(TokenType.REFRESH, refreshTokenExpiration);
        typeToExpiration.put(TokenType.REGISTRATION, registrationTokenExpiration);
        typeToExpiration.put(TokenType.PASSWORD_RESET, passwordResetTokenExpiration);
    }

    public void createVerificationToken(User user, String value, TokenType tokenType) {
        VerificationToken token = new VerificationToken();
        token.setId(sequenceGeneratorService.generateSequence(VerificationToken.SEQUENCE_NAME));
        token.setToken(value);
        token.setUser(user);
        Integer expiration = typeToExpiration.get(tokenType);
        token.setExpiryDate(VerificationToken.calculateExpiryDate(expiration));
        token.setTokenType(tokenType);
        verificationTokenRepository.save(token);
    }

    public VerificationToken validateToken(String value, TokenType tokenType) {
        VerificationToken token = findVerificationToken(value, tokenType);
        Calendar cal = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new ValidationException("Token has expired");
        }
        deleteVerificationToken(token);
        return token;
    }

    public VerificationToken findVerificationToken(String value, TokenType tokenType) {
        return getByValueAndTokenType(value, tokenType);
    }

    private VerificationToken getByValueAndTokenType(String value, TokenType tokenType) {
        return verificationTokenRepository.findByTokenAndTokenType(value, tokenType)
                .orElseThrow(() -> new NotFoundException(String.format("Token %s not found", value)));
    }

    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }

}
