package com.vadmack.authserver.service;

import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.VerificationToken;
import com.vadmack.authserver.exception.NotFoundException;
import com.vadmack.authserver.repository.VerificationTokenRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public void createVerificationToken(User user, String value) {
        VerificationToken token = new VerificationToken();
        token.setId(sequenceGeneratorService.generateSequence(VerificationToken.SEQUENCE_NAME));
        token.setToken(value);
        token.setUser(user);
        token.setExpiryDate(VerificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
        verificationTokenRepository.save(token);
    }

    public VerificationToken findVerificationToken(String value) {
        return getByValue(value);
    }

    private VerificationToken getByValue(String value) {
        return verificationTokenRepository.findByToken(value)
                .orElseThrow(() -> new NotFoundException(String.format("Token %s not found", value)));
    }

}
