package com.vadmack.authserver.service;

import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.domain.entity.VerificationToken;
import com.vadmack.authserver.exception.NotFoundException;
import com.vadmack.authserver.exception.ValidationException;
import com.vadmack.authserver.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceTest {

    @InjectMocks
    private VerificationTokenService verificationTokenService;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    public void validateTokenSuccess() {
        VerificationToken expected = getToken();

        when(verificationTokenRepository.findByTokenAndTokenType(any(String.class), any(TokenType.class)))
                .thenReturn(Optional.of(expected));

        VerificationToken received = verificationTokenService.validateToken("Token_sample", TokenType.REFRESH);

        Assertions.assertEquals(expected, received);
    }

    @Test
    public void validateTokenNotFound() {
        when(verificationTokenRepository.findByTokenAndTokenType(any(String.class), any(TokenType.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                verificationTokenService.validateToken("Token_sample", TokenType.REFRESH));
    }

    @Test
    public void validateTokenExpired() {
        VerificationToken expected = getToken();
        expected.setExpiryDate(VerificationToken.calculateExpiryDate(-100));

        when(verificationTokenRepository.findByTokenAndTokenType(any(String.class), any(TokenType.class)))
                .thenReturn(Optional.of(expected));

        Assertions.assertThrows(ValidationException.class, () ->
                verificationTokenService.validateToken("Token_sample", TokenType.REFRESH));
    }

    @Test
    public void findVerificationTokenSuccess() {
        VerificationToken expected = getToken();

        when(verificationTokenRepository.findByTokenAndTokenType(any(String.class), any(TokenType.class)))
                .thenReturn(Optional.of(expected));

        VerificationToken received = verificationTokenService.findVerificationToken(
                "Token_sample", TokenType.REFRESH);

        Assertions.assertEquals(expected, received);
    }

    @Test
    public void findVerificationTokenNotFound() {
        when(verificationTokenRepository.findByTokenAndTokenType(any(String.class), any(TokenType.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                verificationTokenService.findVerificationToken("Token_sample", TokenType.REFRESH));
    }

    public VerificationToken getToken() {
        VerificationToken token = new VerificationToken();
        token.setToken("Token_sample");
        token.setTokenType(TokenType.REFRESH);
        token.setExpiryDate(VerificationToken.calculateExpiryDate(120));
        return token;
    }


}
